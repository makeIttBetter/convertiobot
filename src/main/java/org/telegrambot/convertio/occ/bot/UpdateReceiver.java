package org.telegrambot.convertio.occ.bot;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegrambot.convertio.core.model.user.Language;
import org.telegrambot.convertio.core.model.user.Role;
import org.telegrambot.convertio.exceptions.facades.FacadeException;
import org.telegrambot.convertio.exceptions.services.ServiceException;
import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.facades.facades.UserFacade;
import org.telegrambot.convertio.facades.facades.UserMessageFacade;
import org.telegrambot.convertio.occ.bot.commands.Command;
import org.telegrambot.convertio.occ.bot.commands.CommandContainer;
import org.telegrambot.convertio.occ.bot.commands.ErrorHandler;
import org.telegrambot.convertio.occ.bot.entities.*;
import org.telegrambot.convertio.occ.bot.view.callback.CallbackSender;
import org.telegrambot.convertio.occ.bot.view.message.MessageEditor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@Component
@Getter(AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UpdateReceiver {
    private final UserFacade userFacade;
    private final UserMessageFacade userMessageFacade;
    private final MessageEditor messageEditor;
    private final CallbackSender callbackSender;
    private final ErrorHandler errorHandler;
    private final CommandContainer commandContainer;
    private final BotSession botSession;
    private final List<PartialBotApiMethod<? extends Serializable>> apiMethods = new ArrayList<>();
    @Value("${amountOfUserRequestsWithin10MinToBlockUser}")
    private int criticalAmountOfRequests;
    @Value("${periodOfCriticalOverloadInMinutes}")
    private int periodOfCriticalOverloadInMinutes;


    // process received Update
    public Map<UserData, BotResponseWsDTO> handle(GarantBot bot, Update update) {
        Chat chat;
        Message message;
        String chatId;
        String name;
        String userName;
        String input;
        Map<UserData, BotResponseWsDTO> responses = new HashMap<>();
        InputType inputType = null;
        UserData user;

        try {
            if (isMessageWithText(update) || update.hasMessage()) {
                message = update.getMessage();
                chat = update.getMessage().getChat();
                chatId = String.valueOf(message.getFrom().getId());
                name = (chat.getFirstName() != null ? chat.getFirstName() : "") + " " + (chat.getLastName() != null ? chat.getLastName() : "");
                userName = chat.getUserName();
                input = message.getText();
                if (update.getMessage().hasDocument()) {
                    inputType = InputType.FILE;
                } else if (update.getMessage().hasVideo()) {
                    inputType = InputType.VIDEO;
                } else if (update.getMessage().hasAudio()) {
                    inputType = InputType.AUDIO;
                } else if (update.getMessage().hasAnimation()) {
                    inputType = InputType.ANIMATION;
                } else if (update.getMessage().hasPhoto()) {
                    inputType = InputType.PHOTO;
                } else if (update.getMessage().hasVideoNote()) {
                    inputType = InputType.VIDEO_NOTE;
                } else if (update.getMessage().hasSticker()) {
                    inputType = InputType.STICKER;
                    System.out.println(update.getMessage().getSticker());
                } else {
                    inputType = InputType.TEXT;
                }
            } else if (update.hasCallbackQuery()) {
                final CallbackQuery callbackQuery = update.getCallbackQuery();
                message = callbackQuery.getMessage();
                chatId = String.valueOf(callbackQuery.getFrom().getId());
                chat = callbackQuery.getMessage().getChat();
                name = (chat.getFirstName() != null ? chat.getFirstName() : "") + " " + (chat.getLastName() != null ? chat.getLastName() : "");
                userName = chat.getUserName();
                input = callbackQuery.getData();
                inputType = InputType.DATA;
            } else {
                throw new UnsupportedOperationException();
            }
            // get user
            user = processUser(chatId, userName, name, getCurrentDate());
            // show info about current input
            String inputInfo = "\n(" + user.getChatId() + ") " + user.getName() + ": " + inputType + "|" + input;
            log.info(inputInfo);

            // getting userSession
            UserSession userSession = processSession(user);
            // initialising bot context class
            BotContextWsDTO botContextWsDTO = new BotContextWsDTO(update, bot, message, user, input, inputType, user.getLanguage().getResourceBundle(), false, userSession);

            // get command
            Command command = commandContainer.get(botContextWsDTO);
            log.info("Current bot state: " + user.getBotState() + "|" + command.getClass().getSimpleName());
            // execute command
            responses = command.execute(botContextWsDTO);
            // update returned bot state if needed
            for (BotResponseWsDTO botResponse : responses.values()) {
                if (botResponse.isStateChanged() && botResponse.getState() == null) {
                    botResponse.setState(command.returnedState());
                }
            }

            return responses;
        } catch (Exception e) {
            return error500Process(update, e);
        } finally {
            if (responses != null) {
                // hiding previous messages' markups for security reasons
                for (Map.Entry<UserData, BotResponseWsDTO> entry : responses.entrySet()) {
                    if (InputType.TEXT.equals(inputType) || entry.getValue().isDeleteKeyboard()) {
                        entry.getValue().addAll(userMessageFacade.hidePreviousReplyMarkup(entry.getKey()));
                    }
                }
            }
        }
    }


    private UserSession processSession(UserData user) {
        UserSession userSession = botSession.get(user);
        if (userSession == null) {
            userSession = new UserSession();
            botSession.put(user, userSession);
        }
        return userSession;
    }


    private UserData processUser(String chatId, String userName, String name, Date currentDate) throws FacadeException {
        UserData user;
        try {
            user = getUserFacade().findByChatId(chatId);
            if (!user.isAutoBlock() && !user.isAdminBlock()) {
                user.setActivityDate(currentDate);
            }
            if (isStartNewOverloadPeriodTime(user)) {
                user.setOverloadStartTime(currentDate);
                user.setRequestAmount(0);
            }
            if (user.getRequestAmount() > criticalAmountOfRequests) {
                user.setAutoBlock(true);
                user.setBlockDate(currentDate);
            }

            user.setName(name);
            user.setUserName(userName);
            user.setRequestAmount(user.getRequestAmount() + 1);
            getUserFacade().update(user);

        } catch (ServiceException e) {
            // creating new UserData
            Date lastBlockDate = convertToDateViaInstant(LocalDate.now().minusDays(100));
            user = UserData.builder().chatId(chatId).role(Role.CLIENT).activityDate(currentDate).registerDate(currentDate).language(Language.RU).userName(userName).name(name).botState(State.NONE).overloadStartTime(currentDate).blockDate(lastBlockDate).build();
            getUserFacade().create(user);
        }
        return user;
    }

    private Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    private Map<UserData, BotResponseWsDTO> error500Process(Update update, Exception e) {
        log.error("Error 500", e);
        // execute error handler
        return getErrorHandler().handle(update);
    }

    private boolean isMessageWithText(Update update) {
        return !update.hasCallbackQuery() && update.hasMessage() && update.getMessage().hasText();
    }

    private Date getCurrentDate() {
        return new Date();
    }

    private long getMillisFromStartingOverloadPeriodPassed(UserData user) {
        Date overloadStartTime = user.getOverloadStartTime();
        Date now = new Date();

        return now.getTime() - overloadStartTime.getTime();
    }

    private boolean isStartNewOverloadPeriodTime(UserData user) {
        if (user.getOverloadStartTime() != null) {
            long millisPassed = getMillisFromStartingOverloadPeriodPassed(user);
            long millisPeriod = 60000L * periodOfCriticalOverloadInMinutes;
            return millisPassed > millisPeriod;
        } else {
            return true;
        }
    }
}
