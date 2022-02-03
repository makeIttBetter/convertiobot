package org.telegrambot.convertio.aspects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.occ.bot.commands.ErrorHandler;
import org.telegrambot.convertio.occ.bot.entities.BotContextWsDTO;
import org.telegrambot.convertio.occ.bot.entities.BotResponseWsDTO;
import org.telegrambot.convertio.occ.bot.entities.InputType;
import org.telegrambot.convertio.occ.bot.view.callback.CallbackSender;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class DataAnswerAspect {
    private final CallbackSender callbackSender;
    private final ErrorHandler errorHandler;


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
        BotContextWsDTO botContext = (BotContextWsDTO) pjp.getArgs()[0];
        Update update = botContext.getUpdate();
        TelegramLongPollingBot bot = botContext.getBot();
        UserData user = botContext.getUser();
        String chatId = user.getChatId();

        Map<UserData, BotResponseWsDTO> responses =
                (Map<UserData, BotResponseWsDTO>) pjp.proceed(pjp.getArgs());

        try {
            BotResponseWsDTO botResponse = responses.get(user);
            if (botResponse == null ||
                    !botResponse.isAnswerCallbackQueryPresent()
                            && InputType.DATA.equals(botContext.getInputType())) {
                callbackSender.sendCallbackAnswerSimple(update, bot);
            }
        } catch (TelegramApiException e) {
            log.error("Inline query is too old", e);
            try {
                getChat(bot, chatId);
                return new HashMap<>();
            } catch (TelegramApiException ex) {
                return error500Process(update, ex);
            }
        }

        return responses;
    }

    private void getChat(TelegramLongPollingBot bot, String chatId) throws TelegramApiException {
        GetChat getChat = new GetChat();
        getChat.setChatId(chatId);

        bot.execute(getChat);
    }


    private Map<UserData, BotResponseWsDTO> error500Process(Update update, Exception e) {
        log.error("Error 500", e);
        // execute error handler
        return errorHandler.handle(update);
    }

}