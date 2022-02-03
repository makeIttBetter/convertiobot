package org.telegrambot.convertio.occ.bot.entities;

import lombok.*;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.occ.bot.GarantBot;

import java.util.ResourceBundle;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BotContextWsDTO {
    private Update update;
    private GarantBot bot;
    private Message message;
    private UserData user;
    private String input;
    private InputType inputType;
    private ResourceBundle resourceBundle;
    private boolean undo;
    private UserSession userSession;

}
