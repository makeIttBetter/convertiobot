package org.telegrambot.convertio.occ.helpers.validators;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegrambot.convertio.core.model.user.User;

@Data
@NoArgsConstructor
public class ValidationEntity {
    private String input;
    private TelegramLongPollingBot bot;
    private User user;

    public ValidationEntity(String input) {
        this.input = input;
    }

    public ValidationEntity(String input, TelegramLongPollingBot bot, User user) {
        this.input = input;
        this.bot = bot;
        this.user = user;
    }
}
