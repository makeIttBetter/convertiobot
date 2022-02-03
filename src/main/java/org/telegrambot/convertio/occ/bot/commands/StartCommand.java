package org.telegrambot.convertio.occ.bot.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegrambot.convertio.consts.Commands;
import org.telegrambot.convertio.core.model.user.Role;
import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.facades.facades.ReferralFacade;
import org.telegrambot.convertio.occ.bot.annotations.MainPoint;
import org.telegrambot.convertio.occ.bot.entities.BotContextWsDTO;
import org.telegrambot.convertio.occ.bot.entities.BotResponseWsDTO;
import org.telegrambot.convertio.occ.bot.entities.InputType;
import org.telegrambot.convertio.occ.bot.entities.State;
import org.telegrambot.convertio.occ.bot.view.button.ButtonsHelper;
import org.telegrambot.convertio.occ.bot.view.message.MessageBuilder;
import org.telegrambot.convertio.occ.helpers.LanguageHelper;

import java.util.Date;
import java.util.Map;
import java.util.function.Predicate;

@MainPoint
@Component
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StartCommand extends AbstractCommand {
    private final MessageBuilder messageBuilder;
    private final ReferralFacade referralFacade;
    private final ButtonsHelper buttonsHelper;
    private final LanguageHelper languageHelper;

    @Override
    public Map<UserData, BotResponseWsDTO> execute(BotContextWsDTO botContext) {
        if (!Commands.START.equals(input) && input.startsWith(Commands.START)) {
            String payload = input.replace(Commands.START, "").trim();
            referralFacade.updateReferralStatByUserAndPayload(user, payload);
        }

        // Ask for preferred language
        SendMessage sendMessage;
        if (user.getRegisterDate().after(new Date(System.currentTimeMillis() - 10_000))) {
            sendMessage = languageHelper.getChooseLanguageMsg(chatId, resourceBundle);
        } else {
            sendMessage = messageBuilder.getMsgHTML(chatId,
                    resourceBundle.getString("main_menu_text"));
            if (Role.ADMIN.equals(user.getRole()) || Role.OWNER.equals(user.getRole())) {
                buttonsHelper.setAdminsKeyboardFirst(sendMessage, resourceBundle);
            } else if (Role.CLIENT.equals(user.getRole())) {
                buttonsHelper.setClientsKeyboard(sendMessage, resourceBundle);
            }
        }
        responses.get(user).add(sendMessage);

        // forming return value
        responses.get(user).setStateChanged(true);
        return responses;
    }

    @Override
    public Predicate<BotContextWsDTO> predicate() {
        return (bc) -> InputType.TEXT.equals(bc.getInputType())
                && Commands.START.equals(bc.getInput());
    }

    @Override
    public State operatedState() {
        return State.ALL;
    }

    @Override
    public State returnedState() {
        return State.START;
    }


}

