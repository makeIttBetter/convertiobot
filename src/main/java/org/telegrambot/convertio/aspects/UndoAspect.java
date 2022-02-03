package org.telegrambot.convertio.aspects;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegrambot.convertio.consts.Commands;
import org.telegrambot.convertio.exceptions.services.ServiceException;
import org.telegrambot.convertio.facades.data.user.RequestData;
import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.facades.facades.RequestFacade;
import org.telegrambot.convertio.occ.bot.annotations.MainPoint;
import org.telegrambot.convertio.occ.bot.closers.UndoHistoryCloser;
import org.telegrambot.convertio.occ.bot.commands.Command;
import org.telegrambot.convertio.occ.bot.entities.BotContextWsDTO;
import org.telegrambot.convertio.occ.bot.entities.BotResponseWsDTO;

import java.util.Map;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class UndoAspect {
    private final RequestFacade requestFacade;
    private final UndoHistoryCloser requestCloser;

    /**
     * Mapping for the needed method
     */
    @Pointcut("execution(public java.util.Map<org.telegrambot.convertio.facades.data.user.UserData," +
            " org.telegrambot.convertio.occ.bot.entities.BotResponseWsDTO>" +
            " org.telegrambot.convertio.occ.bot.commands.Command.execute(..))")
    private void commandMainMethod() {
    }


    /**
     * Checks and processes commands for the relation to the
     * 'Undo' related operations
     */
    @Around("commandMainMethod()")
    private Object processCommandResponses(ProceedingJoinPoint pjp) throws Throwable {
        BotContextWsDTO botContext = (BotContextWsDTO) pjp.getArgs()[0];
        Map<UserData, BotResponseWsDTO> responses =
                (Map<UserData, BotResponseWsDTO>) pjp.proceed(pjp.getArgs());

        // clear previous undo history if needed
        Command command = (Command) pjp.getThis();
        checkIfNeedToDeletePrevRequests(command, responses);

        // save new undo request if needed
        for (Map.Entry<UserData, BotResponseWsDTO> entry : responses.entrySet()) {
            BotResponseWsDTO botResponse = entry.getValue();
            // execution of 'undo' operation check
            if (botResponse.isUndo() && !Commands.UNDO.equals(botContext.getInput())) {
                saveUndoOperationUserRequest(botContext);
            }
        }

        log.info("Returned state: " + command.returnedState());

        return responses;
    }

    private void saveUndoOperationUserRequest(BotContextWsDTO botContext) {
        // write the realisation
        RequestData request = new RequestData();
        UserData user = botContext.getUser();
        Message message = botContext.getMessage();
        request.setUser(user);
        request.setInput(botContext.getInput());
        request.setMessageId(message.getMessageId());
        request.setMessageText(message.getText());
        request.setInputType(botContext.getInputType());
        request.setBotState(user.getBotState());
        try {
            requestFacade.create(request);
        } catch (ServiceException e) {
            log.error("Can't create request entity.");
        }
    }


    private void checkIfNeedToDeletePrevRequests
            (Command command, Map<UserData, BotResponseWsDTO> responses) {
        // currently executed command
        Class<Command> clazz = (Class<Command>) command.getClass();
        MainPoint annotation = AnnotationUtils
                .findAnnotation(clazz, MainPoint.class);

        if (annotation != null) {
            closeResources(responses);
        }
    }


    private void closeResources(Map<UserData, BotResponseWsDTO> responses) {
        for (Map.Entry<UserData, BotResponseWsDTO> entry : responses.entrySet()) {
            UserData user = entry.getKey();
            requestCloser.close(user);
        }
    }


}
