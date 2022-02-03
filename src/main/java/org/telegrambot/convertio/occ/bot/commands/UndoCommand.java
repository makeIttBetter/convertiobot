package org.telegrambot.convertio.occ.bot.commands;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegrambot.convertio.consts.Commands;
import org.telegrambot.convertio.exceptions.commands.CommandException;
import org.telegrambot.convertio.exceptions.services.ServiceException;
import org.telegrambot.convertio.facades.data.user.RequestData;
import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.facades.facades.RequestFacade;
import org.telegrambot.convertio.occ.bot.entities.BotContextWsDTO;
import org.telegrambot.convertio.occ.bot.entities.BotResponseWsDTO;
import org.telegrambot.convertio.occ.bot.entities.InputType;
import org.telegrambot.convertio.occ.bot.entities.State;
import org.telegrambot.convertio.occ.bot.view.message.MessageBuilder;
import org.telegrambot.convertio.occ.filters.CommandFilter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Predicate;

@Slf4j
@Component
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UndoCommand extends AbstractCommand {
    private final RequestFacade requestFacade;
    private final MessageBuilder messageBuilder;
    private final Set<Command> commands;
    private final CommandFilter commandFilter;

    private State returnState;

    @Override
    public Map<UserData, BotResponseWsDTO> execute(BotContextWsDTO botContext) {
        boolean isDeleteKeyboard = false;
        try {
            RequestData request = requestFacade.getLastAndDeleteByUser(user);
            user.setBotState(request.getBotState());

            String prevInput = request.getInput();
            String prevMessageText = request.getMessageText();
            InputType prevInputType = request.getInputType();
            ResourceBundle prevResourceBundle =
                    request.getUser().getLanguage().getResourceBundle();

            // prev request Message
            Message prevMessage = new Message();
            prevMessage.setText(prevMessageText);
            prevMessage.setMessageId(request.getMessageId());
            // forming bot context
            BotContextWsDTO undoBotContext = BotContextWsDTO.builder()
                    .update(botContext.getUpdate())
                    .bot(bot)
                    .input(prevInput)
                    .inputType(prevInputType)
                    .user(user)
                    .message(prevMessage)
                    .resourceBundle(prevResourceBundle)
                    .undo(true)
                    .userSession(userSession)
                    .build();

            // getting needed command operated state
            Command command = getCommand(undoBotContext);
            returnState = command.returnedState();
            for (Map.Entry<UserData, BotResponseWsDTO> response : command.execute(undoBotContext).entrySet()) {
                BotResponseWsDTO botResponse = response.getValue();
                isDeleteKeyboard = isDeleteKeyboard || isDeleteKeyboard(botResponse);
                responses.put(response.getKey(), response.getValue());
            }

            log.info("Previous bot context -> " + undoBotContext);
            log.info("Undo command -> " + command.getClass().getName());

        } catch (ServiceException | CommandException e) {
            String outText = resourceBundle
                    .getString("unknown_command_message");
            SendMessage sendMessage = messageBuilder.getMsgHTML(chatId, outText);
            isDeleteKeyboard = true;
            responses.get(user).add(sendMessage);
        }
        responses.get(user).setDeleteKeyboard(isDeleteKeyboard);


        return responses;
    }

    private boolean isDeleteKeyboard(BotResponseWsDTO botResponse) {
        Set<Class<? extends PartialBotApiMethod<? extends Serializable>>> classes = new HashSet<>();
        classes.add(SendMessage.class);
        classes.add(SendVideo.class);
        classes.add(SendAudio.class);
        classes.add(SendPoll.class);
        classes.add(SendContact.class);
        classes.add(SendPhoto.class);
        classes.add(SendAnimation.class);

        return botResponse.containsSuchTypes(classes);
    }

    @Override
    public Predicate<BotContextWsDTO> predicate() {
        return (bc) -> InputType.DATA.equals(bc.getInputType())
                && Commands.UNDO.equals(bc.getInput());
    }

    @Override
    public State operatedState() {
        return State.CANCEL;
    }

    @Override
    public State returnedState() {
        return returnState;
    }

    public Command getCommand(BotContextWsDTO botContext) throws CommandException {
        return commandFilter.find(botContext, commands);
    }
}

