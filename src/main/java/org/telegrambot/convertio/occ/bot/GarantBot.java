package org.telegrambot.convertio.occ.bot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegrambot.convertio.exceptions.facades.FacadeException;
import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.facades.data.user.UserMessageData;
import org.telegrambot.convertio.facades.facades.UserMessageFacade;
import org.telegrambot.convertio.facades.tasks.ScheduledService;
import org.telegrambot.convertio.occ.bot.entities.BotResponseWsDTO;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Bot used to provide safe trades on telegram
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GarantBot extends TelegramLongPollingBot {
    private final UpdateReceiver updateReceiver;
    private final UserMessageFacade userMessageFacade;
    private final ScheduledService scheduledService;
    private final ExecutorService executorService
            = Executors.newFixedThreadPool(100);
    @Value("${bot.name}")
    @Getter
    private String botUsername;
    @Value("${bot.token}")
    @Getter
    private String botToken;
    @Value("${botExchangeChannelChatId}")
    @Getter
    private String botExchangeChannelChatId;


    @PostConstruct
    private void init() {
        scheduledService.startScheduledTasks();
    }

    /**
     * The method to which notifications come about
     * actions of users in the bot
     *
     * @param update object with new user action information
     */
    public void onUpdateReceived(Update update) {
        executorService.submit(() -> {
            long before = System.currentTimeMillis();

            if (isMessageFromUser(update)) {
                log.info("\n-------------------------------------------------------------------");
                try {
                    Map<UserData, BotResponseWsDTO> responses = getUpdateReceiver().handle(this, update);

                    long after = System.currentTimeMillis();
                    System.out.println("Time: " + (after - before));

                    for (Map.Entry<UserData, BotResponseWsDTO> entry : responses.entrySet()) {
                        BotResponseWsDTO botResponse = entry.getValue();
                        UserData user = entry.getKey();
                        Set<PartialBotApiMethod<? extends Serializable>> messagesToSend
                                = botResponse.getApiMethods();
                        if (messagesToSend != null && !messagesToSend.isEmpty()) {
                            messagesToSend.forEach(response ->
                            {
                                try {
                                    executeBotApiMethod(response, user, botResponse);
                                } catch (FacadeException e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    log.error("Some unexpected exception", e);
                }
                log.info("\n-------------------------------------------------------------------\n");
            }
        });
    }


    public void executeBotApiMethod
            (PartialBotApiMethod<? extends Serializable> apiMethod, UserData user, BotResponseWsDTO botResponse) throws FacadeException {
        Message message = new Message();

        try {
            if (apiMethod instanceof SendMessage) {
                log.info(((SendMessage) apiMethod).getText());
                message = execute((SendMessage) apiMethod);
            } else if (apiMethod instanceof SendAnimation) {
                log.info(((SendAnimation) apiMethod).getCaption());
                message = execute((SendAnimation) apiMethod);
            } else if (apiMethod instanceof SendPhoto) {
                log.info(((SendPhoto) apiMethod).getCaption());
                message = execute((SendPhoto) apiMethod);
            } else if (apiMethod instanceof SendVideo) {
                log.info(((SendVideo) apiMethod).getCaption());
                message = execute((SendVideo) apiMethod);
            } else if (apiMethod instanceof DeleteMessage) {
                log.info(((DeleteMessage) apiMethod).getMethod());
                execute((DeleteMessage) apiMethod);
            } else if (apiMethod instanceof SendPoll) {
                log.info(((SendPoll) apiMethod).getQuestion());
                execute((SendPoll) apiMethod);
            } else if (apiMethod instanceof EditMessageText) {
                log.info(((EditMessageText) apiMethod).getText());
                execute((EditMessageText) apiMethod);
            } else if (apiMethod instanceof EditMessageReplyMarkup) {
                log.info(((EditMessageReplyMarkup) apiMethod).getMethod());
                execute((EditMessageReplyMarkup) apiMethod);
            } else if (apiMethod instanceof CopyMessage) {
                log.info(((CopyMessage) apiMethod).getMethod());
                execute((CopyMessage) apiMethod);
            }
        } catch (TelegramApiException e) {
            log.error("Unable to execute bot api method", e);
        }
        // saving new replyMarkups in db
        processMessage(user, message, botResponse);
        log.info(message != null
                && message.getMessageId() != null ? message.toString() : "");
    }

    /**
     * Do all necessary checks and actions with
     * executed bot api method
     *
     * @param user    UserData to whom the message is sending
     * @param message Message that was sent to user
     */
    private void processMessage(UserData user, Message message, BotResponseWsDTO botResponse) throws FacadeException {
        if (message != null) {
            Integer messageId = message.getMessageId();
            if (messageId != null) {
                saveMessageWithInlineKeyboard(user, message);
            }
        }
    }

    /**
     * Saving messages with inline markup to remove
     * murkup in the future
     *
     * @param user    UserData to whom the message is sending
     * @param message Message that was sent to user
     */
    private void saveMessageWithInlineKeyboard(UserData user, Message message) throws FacadeException {
        if (message.hasReplyMarkup()) {
            UserMessageData userMessage = UserMessageData.builder()
                    .messageId(message.getMessageId())
                    .user(user)
                    .text(message.getText())
                    .build();
            userMessageFacade.create(userMessage);
            log.info("Message with inlineKeyboard was set.");
        }
    }

    private boolean isMessageFromUser(Update update) {
        return (!update.hasChannelPost() && !update.hasEditedChannelPost())
                || (update.hasCallbackQuery() && update.getCallbackQuery()
                .getMessage().getChat().isUserChat());
    }

    @Lookup
    public UpdateReceiver getUpdateReceiver() {
        return this.updateReceiver;
    }
}