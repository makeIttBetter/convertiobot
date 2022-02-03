package org.telegrambot.convertio.occ.filters;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegrambot.convertio.occ.bot.commands.Command;
import org.telegrambot.convertio.occ.bot.entities.BotContextWsDTO;
import org.telegrambot.convertio.occ.bot.entities.State;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CommandFilter {
    @SuppressWarnings("SpringQualifierCopyableLombok")
    @Qualifier("unsupportedCommand")
    private final Command unsupportedCommand;

    public Command find(BotContextWsDTO botContext, Set<Command> commands) {
        // getting current user state
        State state = botContext.getUser().getBotState();

        Command foundCommand = findByStateAndBotContext(commands, State.ALL, botContext);

        if (foundCommand.equals(unsupportedCommand)) {
            foundCommand = findByStateAndBotContext(commands, State.CANCEL, botContext);

            if (foundCommand.equals(unsupportedCommand)) {
                foundCommand = findByStateAndBotContext(commands, state, botContext);
            }
        }

        return foundCommand;
    }

    private Set<Command> findByState(Set<Command> commands, State state) {
        return commands.stream().filter(command -> command.operatedState() != null
                && command.operatedState().equals(state)).collect(Collectors.toSet());
    }

    private Command findByStateAndBotContext(Set<Command> commands, State state, BotContextWsDTO botContext) {
        return findByState(commands, state).stream()
                .filter(command -> command.predicate().test(botContext))
                .findFirst().orElse(unsupportedCommand);
    }

}
