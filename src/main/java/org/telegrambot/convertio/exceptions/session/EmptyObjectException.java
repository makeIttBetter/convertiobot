package org.telegrambot.convertio.exceptions.session;

public class EmptyObjectException extends Exception {
    public EmptyObjectException() {
    }

    public EmptyObjectException(String message) {
        super(message);
    }

    public EmptyObjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyObjectException(Throwable cause) {
        super(cause);
    }
}
