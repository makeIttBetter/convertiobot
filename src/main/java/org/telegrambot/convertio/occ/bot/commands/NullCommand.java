package org.telegrambot.convertio.occ.bot.commands;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegrambot.convertio.consts.Commands;
import org.telegrambot.convertio.exceptions.commands.CommandException;
import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.occ.bot.entities.BotContextWsDTO;
import org.telegrambot.convertio.occ.bot.entities.BotResponseWsDTO;
import org.telegrambot.convertio.occ.bot.entities.InputType;
import org.telegrambot.convertio.occ.bot.entities.State;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NullCommand extends AbstractCommand {
    private State returnedState = State.ALL;

    @Override
    public Map<UserData, BotResponseWsDTO> execute(BotContextWsDTO botContext) throws CommandException {
        returnedState = user.getBotState();
        return new HashMap<>();
    }

    @Override
    public Predicate<BotContextWsDTO> predicate() {
        return (bc) -> InputType.DATA.equals(bc.getInputType())
                && bc.getInput() != null
                && bc.getInput().equals(Commands.NULL);
    }

    @Override
    public State operatedState() {
        return State.ALL;
    }

    @Override
    public State returnedState() {
        return returnedState;
    }
}
