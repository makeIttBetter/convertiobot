package org.telegrambot.convertio.occ.bot.entities;

import org.springframework.stereotype.Component;
import org.telegrambot.convertio.facades.data.user.UserData;

import java.util.HashMap;
import java.util.Map;

@Component
public class BotSession {
    private final Map<UserData, UserSession> container = new HashMap<>();

    public void put(UserData key, UserSession value) {
        container.put(key, value);
    }

    public UserSession get(UserData key) {
        return container.get(key);
    }

    @Override
    public String toString() {
        return "BotSession{" +
                "container=" + container +
                '}';
    }
}
