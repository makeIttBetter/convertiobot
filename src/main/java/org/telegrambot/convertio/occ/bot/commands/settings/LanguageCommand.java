package org.telegrambot.convertio.occ.bot.commands.settings;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegrambot.convertio.consts.Commands;
import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.occ.bot.commands.AbstractCommand;
import org.telegrambot.convertio.occ.bot.entities.BotContextWsDTO;
import org.telegrambot.convertio.occ.bot.entities.BotResponseWsDTO;
import org.telegrambot.convertio.occ.bot.entities.InputType;
import org.telegrambot.convertio.occ.bot.entities.State;
import org.telegrambot.convertio.occ.bot.view.button.ButtonsHelper;
import org.telegrambot.convertio.occ.bot.view.message.MessageEditor;

import java.util.Map;
import java.util.function.Predicate;


@Component
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LanguageCommand extends AbstractCommand {
    private final MessageEditor messageEditor;
    private final ButtonsHelper buttonsHelper;

    @Override
    public Map<UserData, BotResponseWsDTO> execute(BotContextWsDTO botContext) {
        // Ask for preferred language
        EditMessageText languageMessage = messageEditor.getEditMessageText
                (chatId, messageId, resourceBundle.getString("languageMessage"));

        languageMessage = buttonsHelper.addInlineButtonDown(languageMessage,
                "English", Commands.LANGUAGE_EN);
        languageMessage = buttonsHelper.addInlineButtonDown(languageMessage,
                "Русский", Commands.LANGUAGE_RU);
        buttonsHelper.addInlineButtonDown(languageMessage,
                resourceBundle.getString("backButton"), Commands.UNDO);

        responses.get(user).add(languageMessage);

        // forming return value
        responses.get(user).setStateChanged(true);
        responses.get(user).setUndo(true);
        return responses;
    }

    @Override
    public Predicate<BotContextWsDTO> predicate() {
        return (bc) -> InputType.DATA.equals(bc.getInputType())
                && bc.getResourceBundle().getString("languageButton")
                .equals(bc.getInput());
    }

    @Override
    public State operatedState() {
        return State.SETTINGS;
    }

    @Override
    public State returnedState() {
        return State.LANGUAGE;
    }
}