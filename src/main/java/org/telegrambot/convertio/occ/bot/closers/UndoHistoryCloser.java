package org.telegrambot.convertio.occ.bot.closers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.facades.facades.RequestFacade;
import org.telegrambot.convertio.occ.bot.entities.State;

import java.util.Set;

@Component("undoHistoryCloser")
@RequiredArgsConstructor
public class UndoHistoryCloser implements Closer {
    private final RequestFacade requestFacade;

    @Override
    public void close(UserData user) {
        requestFacade.deleteAllByUser(user);
    }

    @Override
    public Set<State> operatedStates() {
        return Set.of(State.DEALS_LIST, State.CHOSEN_DEAL);
    }
}
