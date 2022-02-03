package org.telegrambot.convertio.occ.bot.commands;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegrambot.convertio.exceptions.services.ServiceException;
import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.facades.facades.UserFacade;
import org.telegrambot.convertio.facades.facades.UserMessageFacade;
import org.telegrambot.convertio.occ.bot.annotations.MainPoint;
import org.telegrambot.convertio.occ.bot.entities.BotResponseWsDTO;
import org.telegrambot.convertio.occ.bot.entities.State;
import org.telegrambot.convertio.occ.bot.view.message.MessageBuilder;
import org.telegrambot.convertio.occ.bot.view.message.MessageEditor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Slf4j
@MainPoint
@Component
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ErrorHandler {
    protected final Map<UserData, BotResponseWsDTO> responses = new HashMap<>();
    private final MessageBuilder messageBuilder;
    private final MessageEditor messageEditor;
    private final UserFacade userFacade;
    private final UserMessageFacade userMessageFacade;

    /**
     * Handle unexpected internal errors
     *
     * @return List with telegram methods that returned to user
     * to notify him about internal Error
     */
    public Map<UserData, BotResponseWsDTO> handle(Update update) {
        Message message;
        String chatId = null;

        PartialBotApiMethod<? extends Serializable> errorMessage = null;
        if (isMessageWithText(update)) {
            message = update.getMessage();
            chatId = String.valueOf(message.getFrom().getId());

            ResourceBundle resourceBundle = getResourceBundle(chatId);

            errorMessage = messageBuilder.getMsgHTML(chatId,
                    resourceBundle.getString("unexpected.internal.error"));
        } else if (update.hasCallbackQuery()) {
            final CallbackQuery callbackQuery = update.getCallbackQuery();
            message = callbackQuery.getMessage();
            chatId = String.valueOf(callbackQuery.getFrom().getId());

            ResourceBundle resourceBundle = getResourceBundle(chatId);

            errorMessage = messageEditor.getEditMessageText
                    (chatId, message.getMessageId(), message.getText() +
                            "\n\n" + resourceBundle.getString("unexpected.internal.error"));
        }
        try {
            UserData user = userFacade.findByChatId(chatId);
            responses.put(user, new BotResponseWsDTO());
            userFacade.updateUserState(user, State.NONE);
            // setting response values
            responses.get(user).add(errorMessage);
            responses.get(user).setState(State.NONE);

            // hiding previous messages' markups for security reasons
            responses.get(user).addAll(userMessageFacade.hidePreviousReplyMarkup(user));
        } catch (Exception e) {
            log.error("Error updating user status", e);
        }
        return responses;
    }

    private ResourceBundle getResourceBundle(String chatId) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle
                ("resources", new Locale("en", "US"));
        try {
            UserData user = userFacade.findByChatId(chatId);
            resourceBundle = user.getLanguage().getResourceBundle();
        } catch (ServiceException ignored) {
        }
        return resourceBundle;
    }


    private boolean isMessageWithText(Update update) {
        return !update.hasCallbackQuery() && update.hasMessage() && update.getMessage().hasText();
    }


}
