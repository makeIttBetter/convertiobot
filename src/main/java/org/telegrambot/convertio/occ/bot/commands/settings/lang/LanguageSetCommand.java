package org.telegrambot.convertio.occ.bot.commands.settings.lang;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegrambot.convertio.core.model.user.Language;
import org.telegrambot.convertio.core.model.user.Role;
import org.telegrambot.convertio.exceptions.facades.FacadeException;
import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.facades.facades.UserFacade;
import org.telegrambot.convertio.occ.bot.commands.AbstractCommand;
import org.telegrambot.convertio.occ.bot.entities.BotContextWsDTO;
import org.telegrambot.convertio.occ.bot.entities.BotResponseWsDTO;
import org.telegrambot.convertio.occ.bot.entities.InputType;
import org.telegrambot.convertio.occ.bot.entities.State;
import org.telegrambot.convertio.occ.bot.view.button.ButtonsHelper;
import org.telegrambot.convertio.occ.bot.view.message.MessageBuilder;
import org.telegrambot.convertio.occ.bot.view.message.MessageDeleter;

import java.util.Map;
import java.util.function.Predicate;


@Component
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LanguageSetCommand extends AbstractCommand {
    private final UserFacade userFacade;
    private final MessageBuilder messageBuilder;
    private final MessageDeleter messageDeleter;
    private final ButtonsHelper buttonsHelper;
    @Value("${bot.user.name.text}")
    private String botInText;

    @Override
    public Map<UserData, BotResponseWsDTO> execute(BotContextWsDTO botContext) throws FacadeException {
        // updating user in DB
        user.setLanguage(Language.getStateByName(input));
        userFacade.update(user);

        resourceBundle = user.getLanguage().getResourceBundle();
        // delete previous message
        DeleteMessage deleteMessage = messageDeleter
                .getDeleteMessage(chatId, messageId);
        responses.get(user).add(deleteMessage);

        // Lang was successfully set
        SendMessage langWasSetMsg = messageBuilder.getMsgWithoutMarkup
                (chatId, resourceBundle.getString("successLanguageMessage"));
        if (user.getRole().equals(Role.ADMIN) || user.getRole().equals(Role.OWNER)) {
            buttonsHelper.setAdminsKeyboardFirst(langWasSetMsg, resourceBundle);
        } else if (user.getRole().equals(Role.CLIENT)) {
            buttonsHelper.setClientsKeyboard(langWasSetMsg, resourceBundle);
        }
        responses.get(user).add(langWasSetMsg);

//        // Greet user
//        String greetingText = String.format
//                (resourceBundle.getString("welcome.message.text"), " " + botInText);
//        SendMessage welcomeMessage = messageFormer.getMsgHTML(chatId, greetingText);
//        responses.get(user).add(welcomeMessage);

        // forming return value
        responses.get(user).setStateChanged(true);
        return responses;
    }

    @Override
    public Predicate<BotContextWsDTO> predicate() {
        return (bc) -> {
            if (InputType.DATA.equals(bc.getInputType())) {
                for (Language lang : Language.values()) {
                    if (lang.getName().equals(bc.getInput())) {
                        return true;
                    }
                }
            }
            return false;
        };
    }

    @Override
    public State operatedState() {
        return State.LANGUAGE;
    }

    @Override
    public State returnedState() {
        return State.NONE;
    }
}
