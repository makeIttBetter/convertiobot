package org.telegrambot.convertio.exceptions.validators;

public class BotIsNotAddedToTheChannelException extends ValidationException {
    public BotIsNotAddedToTheChannelException() {
        super();
    }

    public BotIsNotAddedToTheChannelException(String message) {
        super(message);
    }

    public BotIsNotAddedToTheChannelException(String message, Throwable cause) {
        super(message, cause);
    }

    public BotIsNotAddedToTheChannelException(Throwable cause) {
        super(cause);
    }
}
