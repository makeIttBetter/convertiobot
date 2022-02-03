package org.telegrambot.convertio.occ.bot.commands.search;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.occ.bot.commands.AbstractCommand;
import org.telegrambot.convertio.occ.bot.entities.BotContextWsDTO;
import org.telegrambot.convertio.occ.bot.entities.BotResponseWsDTO;
import org.telegrambot.convertio.occ.bot.entities.InputType;
import org.telegrambot.convertio.occ.bot.entities.State;
import org.telegrambot.convertio.occ.bot.view.button.ButtonsHelper;
import org.telegrambot.convertio.occ.bot.view.message.MessageBuilder;

import java.util.Map;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SearchFileTypeCommand extends AbstractCommand {
    private final MessageBuilder messageBuilder;
    private final ButtonsHelper buttonsHelper;

    @Override
    public Map<UserData, BotResponseWsDTO> execute(BotContextWsDTO botContext) {

        String text = resourceBundle.getString("searchFileType.text");
        SendMessage searchFormatMessage = messageBuilder.getMsgHTML(chatId, text);

        responses.get(user).add(searchFormatMessage);

        // forming return value
        responses.get(user).setStateChanged(true);
        responses.get(user).setUndo(true);

        // forming return value
        responses.get(user).setStateChanged(true);
        responses.get(user).setUndo(true);
        return responses;
    }

    @Override
    public Predicate<BotContextWsDTO> predicate() {
        return (bc) -> InputType.TEXT.equals(bc.getInputType())
                ;
    }

    @Override
    public State operatedState() {
        return State.ALL;
    }

    @Override
    public State returnedState() {
        return State.SEARCH_FORMAT;
    }
}
