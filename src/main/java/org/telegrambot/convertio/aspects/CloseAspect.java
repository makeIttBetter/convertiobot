package org.telegrambot.convertio.aspects;

import com.google.protobuf.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.telegrambot.convertio.exceptions.services.ObjectNotFoundException;
import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.occ.bot.annotations.MainPoint;
import org.telegrambot.convertio.occ.bot.closers.Closer;
import org.telegrambot.convertio.occ.bot.commands.Command;
import org.telegrambot.convertio.occ.bot.entities.BotResponseWsDTO;
import org.telegrambot.convertio.occ.helpers.CloserHelper;

import java.util.Map;
import java.util.Set;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CloseAspect {
    private final CloserHelper closerHelper;
    private final Set<Closer> closers;


    /**
     * Mapping for the needed method
     */
    @Pointcut("execution(public java.util.Map<org.telegrambot.convertio.facades.data.user.UserData," +
            " org.telegrambot.convertio.occ.bot.entities.BotResponseWsDTO>" +
            " org.telegrambot.convertio.occ.bot.commands.Command.execute(..))")
    private void commandMainMethod() {
    }

    /**
     * Method is user to close resources if it's needed
     */
    @Around("commandMainMethod()")
    public Object checkIfNeedToCloseResources(ProceedingJoinPoint pjp) throws Throwable {
        Map<UserData, BotResponseWsDTO> responses =
                (Map<UserData, BotResponseWsDTO>) pjp.proceed(pjp.getArgs());

        // currently executed command
        Command command = (Command) pjp.getThis();
        Class<Command> clazz = (Class<Command>) command.getClass();
        MainPoint annotation = AnnotationUtils
                .findAnnotation(clazz, MainPoint.class);

        if (annotation != null) {
            log.info("Resources are closed");
            closeResources(responses);
        }
        return responses;
    }

    private void closeResources(Map<UserData, BotResponseWsDTO> responses) {
        for (Map.Entry<UserData, BotResponseWsDTO> entry : responses.entrySet()) {
            UserData user = entry.getKey();

            // execution of closer if found
            try {
                Closer closer = closerHelper
                        .find(closers, user.getBotState());
                closer.close(user);
            } catch (ServiceException | ObjectNotFoundException ignored) {
            }
        }
    }

}
