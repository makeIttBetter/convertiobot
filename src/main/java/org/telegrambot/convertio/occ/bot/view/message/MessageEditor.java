package org.telegrambot.convertio.occ.bot.view.message;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegrambot.convertio.occ.helpers.ErrorMessageHelper;


@Component
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MessageEditor {
    private final ErrorMessageHelper errorMessageHelper;

    public EditMessageText getEditMessageText(String chatId, Integer messageId, String text) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setMessageId(messageId);
        editMessageText.setChatId(chatId);
        editMessageText.setText(text);
        editMessageText.enableHtml(true);
        editMessageText.disableWebPagePreview();

        return editMessageText;
    }

    public EditMessageText getEditMessageText(String chatId, Integer messageId) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setMessageId(messageId);
        editMessageText.setChatId(chatId);
        editMessageText.enableHtml(true);
        editMessageText.disableWebPagePreview();

        return editMessageText;
    }

    public EditMessageReplyMarkup getEditMessageReplyMarkup(String chatId, Integer messageId) {
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setMessageId(messageId);
        editMessageReplyMarkup.setChatId(chatId);

        return editMessageReplyMarkup;
    }

    public EditMessageText editMessageWithError
            (String chatId, int messageId, String messageText, String errorText) {
        String outMessageText = errorMessageHelper.concat(messageText, errorText);
        return getEditMessageText(chatId, messageId, outMessageText);
    }
}
