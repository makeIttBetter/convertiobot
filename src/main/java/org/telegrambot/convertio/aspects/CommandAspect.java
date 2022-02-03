package org.telegrambot.convertio.aspects;


import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.telegrambot.convertio.occ.bot.commands.Command;
import org.telegrambot.convertio.occ.bot.entities.BotContextWsDTO;

@Aspect
@Component
@RequiredArgsConstructor
public class CommandAspect {

    /**
     * Mapping for the needed method
     */
    @Pointcut("execution(public java.util.Map<org.telegrambot.convertio.facades.data.user.UserData," +
            " org.telegrambot.convertio.occ.bot.entities.BotResponseWsDTO>" +
            " org.telegrambot.convertio.occ.bot.commands.Command.execute(..))")
    private void commandMainMethod() {
    }


    /**
     * Initialize fields in Commands before every execution
     */
    @Before("commandMainMethod()")
    public void initBefore(JoinPoint joinPoint) {
        Command command = (Command) joinPoint.getThis();
        command.init((BotContextWsDTO) joinPoint.getArgs()[0]);
    }

}
