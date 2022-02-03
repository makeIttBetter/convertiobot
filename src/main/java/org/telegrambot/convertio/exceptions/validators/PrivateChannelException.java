package org.telegrambot.convertio.exceptions.validators;

public class PrivateChannelException extends ValidationException {
    public PrivateChannelException() {
        super();
    }

    public PrivateChannelException(String message) {
        super(message);
    }

    public PrivateChannelException(String message, Throwable cause) {
        super(message, cause);
    }

    public PrivateChannelException(Throwable cause) {
        super(cause);
    }
}
