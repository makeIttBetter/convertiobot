package org.telegrambot.convertio.occ.bot.entities;

import org.telegrambot.convertio.exceptions.session.EmptyObjectException;

import java.util.HashMap;
import java.util.Map;

public class UserSession {
    private final Map<String, Object> container = new HashMap<>();

    public void put(String key, Object value) {
        container.put(key, value);
    }

    public void remove(String key) {
        container.remove(key);
    }

    public Object get(String key) throws EmptyObjectException {
        Object o = container.get(key);
        if (o == null) {
            throw new EmptyObjectException("Object not found in user session.");
        }

        return o;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "container=" + container +
                '}';
    }
}
