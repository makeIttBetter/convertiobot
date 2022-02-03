package org.telegrambot.convertio.exceptions.validators;

public class ChannelDidNotTransferredException extends ValidationException {
    public ChannelDidNotTransferredException() {
        super();
    }

    public ChannelDidNotTransferredException(String message) {
        super(message);
    }

    public ChannelDidNotTransferredException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChannelDidNotTransferredException(Throwable cause) {
        super(cause);
    }
}
