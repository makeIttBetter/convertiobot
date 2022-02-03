package org.telegrambot.convertio.exceptions.validators;

public class BotDidNotAddedToTheChannelException extends ValidationException {
    public BotDidNotAddedToTheChannelException() {
        super();
    }

    public BotDidNotAddedToTheChannelException(String message) {
        super(message);
    }

    public BotDidNotAddedToTheChannelException(String message, Throwable cause) {
        super(message, cause);
    }

    public BotDidNotAddedToTheChannelException(Throwable cause) {
        super(cause);
    }
}
