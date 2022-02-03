package org.telegrambot.convertio.occ.bot.commands.filetypes;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegrambot.convertio.consts.Commands;
import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.occ.bot.annotations.MainPoint;
import org.telegrambot.convertio.occ.bot.commands.AbstractCommand;
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
public class CategoryFilesListCommand extends AbstractCommand {
    private final MessageBuilder messageBuilder;
    private final ButtonsHelper buttonsHelper;

    @Override
    public Map<UserData, BotResponseWsDTO> execute(BotContextWsDTO botContext) {



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
        return State.FORMAT_LIST;
    }

    @Override
    public State returnedState() {
        return State.FORMAT_WRITABLE_TYPES;
    }

}

