package org.telegrambot.convertio.exceptions.facades;

public class FacadeCreateException extends FacadeException {
    public FacadeCreateException() {
        super();
    }

    public FacadeCreateException(String message) {
        super(message);
    }

    public FacadeCreateException(String message, Throwable cause) {
        super(message, cause);
    }

    public FacadeCreateException(Throwable cause) {
        super(cause);
    }
}
