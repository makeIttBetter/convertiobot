package org.telegrambot.convertio.occ.bot.commands;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
public class InfoCommand extends AbstractCommand {
    private final MessageBuilder messageBuilder;
    private final ButtonsHelper buttonsHelper;

    @Value("${bot.support.link}")
    @Setter
    private String supportLink;
    @Value("${bot.rules.link}")
    @Setter
    private String botRulesLink;
    @Value("${bot.instructions.link}")
    @Setter
    private String botInstructionLink;

    @Override
    public Map<UserData, BotResponseWsDTO> execute(BotContextWsDTO botContext) {
        // Ask for preferred language
        SendMessage infoMessage = messageBuilder.getMsgHTML(chatId, messageText);
        buttonsHelper.addInlineButtonRedirectDown(infoMessage,
                resourceBundle.getString("helpButton"), supportLink);
        buttonsHelper.addInlineButtonRedirectDown(infoMessage,
                resourceBundle.getString("rulesButton"), botRulesLink);
        buttonsHelper.addInlineButtonRedirectDown(infoMessage,
                resourceBundle.getString("instructionsButton"), botInstructionLink);
        buttonsHelper.addInlineButtonDown(infoMessage,
                resourceBundle.getString("currLimitsButton"), Commands.CURRENCIES_LIMITS_COMMAND);
        responses.get(user).add(infoMessage);

        // forming return value
        responses.get(user).setStateChanged(true);
        responses.get(user).setUndo(true);

        return responses;
    }

    @Override
    public Predicate<BotContextWsDTO> predicate() {
        return (bc) -> InputType.TEXT.equals(bc.getInputType())
                && (Commands.INFO.equals(bc.getInput())
                || bc.getResourceBundle().getString("infoButton")
                .equals(bc.getInput()));
    }

    @Override
    public State operatedState() {
        return State.ALL;
    }

    @Override
    public State returnedState() {
        return State.INFO;
    }
}

