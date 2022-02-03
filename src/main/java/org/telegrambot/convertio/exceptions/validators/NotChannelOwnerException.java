package org.telegrambot.convertio.exceptions.validators;

public class NotChannelOwnerException extends ValidationException {
    public NotChannelOwnerException() {
        super();
    }

    public NotChannelOwnerException(String message) {
        super(message);
    }

    public NotChannelOwnerException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotChannelOwnerException(Throwable cause) {
        super(cause);
    }
}
