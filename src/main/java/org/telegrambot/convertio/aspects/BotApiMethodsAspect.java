package org.telegrambot.convertio.aspects;


import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.facades.facades.UserMessageFacade;
import org.telegrambot.convertio.occ.bot.entities.BotResponseWsDTO;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Aspect
@Component
@RequiredArgsConstructor
public class BotApiMethodsAspect {
    private final UserMessageFacade userMessageFacade;

    /**
     * Mapping for the needed method
     */
    @Pointcut("execution(public java.util.Map<org.telegrambot.convertio.facades.data.user.UserData," + " org.telegrambot.convertio.occ.bot.entities.BotResponseWsDTO>" + " org.telegrambot.convertio.occ.bot.UpdateReceiver.handle(..))")
    private void updateReceiverMainMethod() {
    }


    /**
     * Used to delete message with reply markup from db.
     * Because it can be duplicate method execution in the future.
     * For example: user press button "Cancel" inline keyboard
     * is removed, but then user send new message, in db this message
     * is still exists, so we get it from db, create new EditMessageReplyMarkup
     * but actually this message as no inline reply markup, so it will be
     * waste of our program resources
     *
     * @param responses return value of commands main method execution
     *                  (bot api methods list to execute)
     */
    @AfterReturning(pointcut = "updateReceiverMainMethod()", returning = "responses")
    private void processCommandResponses(Map<UserData, BotResponseWsDTO> responses) {
        for (Map.Entry<UserData, BotResponseWsDTO> entry : responses.entrySet()) {
            BotResponseWsDTO botResponse = entry.getValue();
            UserData user = entry.getKey();
            Set<PartialBotApiMethod<? extends Serializable>> botApiMethods = botResponse.getApiMethods();

            deleteMessageIfNeeded(user, botApiMethods);
        }

    }


    private void deleteMessageIfNeeded(UserData user, Set<PartialBotApiMethod<? extends Serializable>> methods) {
        Set<Integer> messageIdList = new HashSet<>();

        for (PartialBotApiMethod<? extends Serializable> method : methods) {
            if (user != null) {
                if (method instanceof EditMessageText) {
                    EditMessageText editMessageText = (EditMessageText) method;
                    Integer messageId = editMessageText.getMessageId();
                    InlineKeyboardMarkup markup = editMessageText.getReplyMarkup();
                    if (messageId != null && messageId != 0 && (markup == null || markup.getKeyboard().isEmpty())) {
                        messageIdList.add(messageId);
                    }
                } else if (method instanceof EditMessageReplyMarkup) {
                    EditMessageReplyMarkup editMessageReplyMarkup = (EditMessageReplyMarkup) method;
                    Integer messageId = editMessageReplyMarkup.getMessageId();
                    InlineKeyboardMarkup markup = editMessageReplyMarkup.getReplyMarkup();
                    if (messageId != null && messageId != 0 && (markup == null || markup.getKeyboard().isEmpty())) {
                        messageIdList.add(messageId);
                    }
                }
            }
        }
        userMessageFacade.deleteByUserAndMessageId(user, messageIdList);
    }
}
