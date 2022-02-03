package org.telegrambot.convertio.occ.helpers;

import com.google.protobuf.ServiceException;
import org.springframework.stereotype.Component;
import org.telegrambot.convertio.exceptions.services.ObjectNotFoundException;
import org.telegrambot.convertio.occ.bot.closers.Closer;
import org.telegrambot.convertio.occ.bot.entities.State;

import java.util.Set;

@Component
public class CloserHelper {

    public Closer find(Set<Closer> closers, State state) throws ServiceException, ObjectNotFoundException {

        for (Closer closer : closers) {
            if (closer.operatedStates().contains(state)) {
                return closer;
            }
        }
        throw new ObjectNotFoundException("Closer not found for current state;");
    }

}
