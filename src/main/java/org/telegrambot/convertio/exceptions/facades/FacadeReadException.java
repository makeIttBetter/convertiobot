package org.telegrambot.convertio.exceptions.facades;

public class FacadeReadException extends FacadeException {
    public FacadeReadException() {
        super();
    }

    public FacadeReadException(String message) {
        super(message);
    }

    public FacadeReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public FacadeReadException(Throwable cause) {
        super(cause);
    }
}
