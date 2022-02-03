package org.telegrambot.convertio.occ.bot.closers;

import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.occ.bot.entities.State;

import java.util.Set;

public interface Closer {
    void close(UserData user);

    Set<State> operatedStates();
}
