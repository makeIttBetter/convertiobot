package org.telegrambot.convertio.occ.bot.commands;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegrambot.convertio.exceptions.commands.CommandException;
import org.telegrambot.convertio.occ.bot.entities.BotContextWsDTO;
import org.telegrambot.convertio.occ.filters.CommandFilter;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CommandContainer {
    private final Set<Command> commands;
    private final CommandFilter commandFilter;

    public Command get(BotContextWsDTO botContext) throws CommandException {
        return commandFilter.find(botContext, commands);
    }

}
