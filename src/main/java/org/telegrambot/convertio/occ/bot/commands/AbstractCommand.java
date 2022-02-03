package org.telegrambot.convertio.occ.bot.commands;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.occ.bot.GarantBot;
import org.telegrambot.convertio.occ.bot.entities.BotContextWsDTO;
import org.telegrambot.convertio.occ.bot.entities.BotResponseWsDTO;
import org.telegrambot.convertio.occ.bot.entities.UserSession;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

@Setter
@Getter(AccessLevel.PROTECTED)
public abstract class AbstractCommand implements Command {
    protected final Map<UserData, BotResponseWsDTO> responses = new HashMap<>();
    protected GarantBot bot;
    protected Message message;
    protected int messageId;
    protected String messageText;
    protected UserData user;
    protected String chatId;
    protected String input;
    protected ResourceBundle resourceBundle;
    protected UserSession userSession;

    @Override
    public void init(BotContextWsDTO botContext) {
        this.user = botContext.getUser();
        this.chatId = user.getChatId();
        this.resourceBundle = botContext.getResourceBundle();
        this.input = botContext.getInput();
        this.bot = botContext.getBot();
        this.message = botContext.getMessage();
        this.messageId = message.getMessageId();
        this.messageText = message.getText();
        this.userSession = botContext.getUserSession();
        responses.put(user, new BotResponseWsDTO());
    }
}