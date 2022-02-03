package org.telegrambot.convertio.occ.bot.view.callback;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CallbackSender {

    public void sendCallbackAnswer(Update update, TelegramLongPollingBot bot, String text) {
        CallbackQuery query = update.getCallbackQuery();
        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(query.getId());
        answer.setText(text);
        answer.setShowAlert(true);
        try {
            bot.execute(answer);
        } catch (TelegramApiException e) {
            System.err.println("CallbackQuery sending error: " + e.getLocalizedMessage());
        }
    }

    public void sendCallbackAnswerSimple(Update update, TelegramLongPollingBot bot) throws TelegramApiException {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(callbackQuery.getId());

        bot.execute(answerCallbackQuery);
    }
}
