package org.telegrambot.convertio.exceptions.validators;

public class UnknownChannelTopicException extends ValidationException {
    public UnknownChannelTopicException() {
        super();
    }

    public UnknownChannelTopicException(String message) {
        super(message);
    }

    public UnknownChannelTopicException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownChannelTopicException(Throwable cause) {
        super(cause);
    }
}
