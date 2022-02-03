package org.telegrambot.convertio.occ.bot.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegrambot.convertio.core.model.user.Role;
import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.occ.bot.annotations.MainPoint;
import org.telegrambot.convertio.occ.bot.entities.BotContextWsDTO;
import org.telegrambot.convertio.occ.bot.entities.BotResponseWsDTO;
import org.telegrambot.convertio.occ.bot.entities.State;
import org.telegrambot.convertio.occ.bot.view.button.ButtonsHelper;
import org.telegrambot.convertio.occ.bot.view.message.MessageBuilder;

import java.util.Map;
import java.util.function.Predicate;

@MainPoint
@Component("unsupportedCommand")
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UnsupportedCommand extends AbstractCommand {
    private final MessageBuilder messageBuilder;
    private final ButtonsHelper buttonsHelper;

    @Override
    public Map<UserData, BotResponseWsDTO> execute(BotContextWsDTO botContext) {

        SendMessage sendMessage = messageBuilder.getMsgHTML(chatId,
                resourceBundle.getString("unknown_command_message"));
        if (Role.ADMIN.equals(user.getRole()) || Role.OWNER.equals(user.getRole())) {
            buttonsHelper.setAdminsKeyboardFirst(sendMessage, resourceBundle);
        } else if (Role.CLIENT.equals(user.getRole())) {
            buttonsHelper.setClientsKeyboard(sendMessage, resourceBundle);
        }
        responses.get(user).add(sendMessage);

        // forming return value
        responses.get(user).setStateChanged(true);
        responses.get(user).setDeleteKeyboard(true);
        return responses;
    }

    @Override
    public Predicate<BotContextWsDTO> predicate() {
        return (bc) -> false;
    }

    @Override
    public State operatedState() {
        return State.ALL;
    }

    @Override
    public State returnedState() {
        return State.NONE;
    }
}

