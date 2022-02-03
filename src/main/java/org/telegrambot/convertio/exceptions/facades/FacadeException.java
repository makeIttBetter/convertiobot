package org.telegrambot.convertio.exceptions.facades;

import org.telegrambot.convertio.exceptions.services.ServiceException;

public class FacadeException extends ServiceException {
    public FacadeException() {
        super();
    }

    public FacadeException(String message) {
        super(message);
    }

    public FacadeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FacadeException(Throwable cause) {
        super(cause);
    }
}
