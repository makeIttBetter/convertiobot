package org.telegrambot.convertio.exceptions.facades;

public class FacadeUpdateException extends FacadeException {
    public FacadeUpdateException() {
        super();
    }

    public FacadeUpdateException(String message) {
        super(message);
    }

    public FacadeUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public FacadeUpdateException(Throwable cause) {
        super(cause);
    }
}
