package org.telegrambot.convertio.occ.bot.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegrambot.convertio.consts.Commands;
import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.occ.bot.annotations.MainPoint;
import org.telegrambot.convertio.occ.bot.entities.BotContextWsDTO;
import org.telegrambot.convertio.occ.bot.entities.BotResponseWsDTO;
import org.telegrambot.convertio.occ.bot.entities.InputType;
import org.telegrambot.convertio.occ.bot.entities.State;
import org.telegrambot.convertio.occ.bot.view.button.ButtonsHelper;
import org.telegrambot.convertio.occ.bot.view.message.MessageBuilder;

import java.util.Map;
import java.util.function.Predicate;

@MainPoint
@Component
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SettingsCommand extends AbstractCommand {
    private final MessageBuilder messageBuilder;
    private final ButtonsHelper buttonsHelper;
    @Value("${bot.support.user.name.text}")
    private String botSupportInText;

    @Override
    public Map<UserData, BotResponseWsDTO> execute(BotContextWsDTO botContext) {
        SendMessage sendMessage = messageBuilder.getMsgHTML(chatId, messageText);
        buttonsHelper.addInlineButtonDown(sendMessage,
                resourceBundle.getString("languageButton"), resourceBundle.getString("languageButton"));

        responses.get(user).add(sendMessage);

        // forming return value
        responses.get(user).setStateChanged(true);
        responses.get(user).setUndo(true);

        return responses;
    }

    @Override
    public Predicate<BotContextWsDTO> predicate() {
        return (bc) -> InputType.TEXT.equals(bc.getInputType())
                && (Commands.SETTINGS.equals(bc.getInput())
                || bc.getResourceBundle().getString("settingsButton")
                .equals(bc.getInput()));
    }

    @Override
    public State operatedState() {
        return State.ALL;
    }

    @Override
    public State returnedState() {
        return State.SETTINGS;
    }

}
