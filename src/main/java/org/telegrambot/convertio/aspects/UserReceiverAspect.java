package org.telegrambot.convertio.aspects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.telegrambot.convertio.exceptions.facades.FacadeException;
import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.facades.facades.UserFacade;
import org.telegrambot.convertio.occ.bot.entities.BotResponseWsDTO;
import org.telegrambot.convertio.occ.bot.entities.State;

import java.util.Map;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class UserReceiverAspect {
    private final UserFacade userFacade;

    /**
     * Mapping for the needed method
     */
    @Pointcut("execution(public java.util.Map<org.telegrambot.convertio.facades.data.user.UserData," + " org.telegrambot.convertio.occ.bot.entities.BotResponseWsDTO>" + " org.telegrambot.convertio.occ.bot.UpdateReceiver.handle(..))")
    private void updateReceiverMainMethod() {
    }


    /**
     * Used to delete message with reply markup from db.
     * Because it can be duplicate method execution in the future.
     * For example: user press button "Cancel" inline keyboard
     * is removed, but then user send new message, in db this message
     * is still exists, so we get it from db, create new EditMessageReplyMarkup
     * but actually this message as no inline reply markup, so it will be
     * waste of our program resources
     *
     * @param responses return value of commands main method execution
     *                  (bot api methods list to execute)
     */
    @AfterReturning(pointcut = "updateReceiverMainMethod()", returning = "responses")
    private void processCommandResponses(Map<UserData, BotResponseWsDTO> responses) throws FacadeException {
        for (Map.Entry<UserData, BotResponseWsDTO> entry : responses.entrySet()) {
            BotResponseWsDTO botResponse = entry.getValue();
            UserData user = entry.getKey();
            // execution of user update
            updateUserIfNeeded(user, botResponse);
        }
    }


    /**
     * Updates user state if state is needed to be changed;
     *
     * @param user             current user
     * @param botResponseWsDTO response to user
     */
    private void updateUserIfNeeded(UserData user, BotResponseWsDTO botResponseWsDTO) throws FacadeException {
        // update user and his message if needed
        if (botResponseWsDTO.isStateChanged()) {
            State state = botResponseWsDTO.getState();
            // update user state
            userFacade.updateUserState(user, state);
            log.info("User state was set. -> " + state);
        }
    }
}
