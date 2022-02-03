package org.telegrambot.convertio.occ.helpers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegrambot.convertio.consts.Commands;
import org.telegrambot.convertio.occ.bot.view.button.ButtonsHelper;
import org.telegrambot.convertio.occ.bot.view.message.MessageBuilder;

import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LanguageHelper {
    private final ButtonsHelper buttonsHelper;
    private final MessageBuilder messageBuilder;

    public SendMessage getChooseLanguageMsg
            (String chatId, ResourceBundle resourceBundle) {

        String mess = resourceBundle.getString("languageMessage");
        SendMessage sendMessage = messageBuilder.getMsgWithoutMarkup(chatId, mess);
        sendMessage = buttonsHelper.addInlineButtonDown(sendMessage,
                "English", Commands.LANGUAGE_EN);
        sendMessage = buttonsHelper.addInlineButtonDown(sendMessage,
                "Русский", Commands.LANGUAGE_RU);

        return sendMessage;
    }
}
