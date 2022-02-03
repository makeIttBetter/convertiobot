package org.telegrambot.convertio.exceptions.validators;

public class UserAreNotTheOwnerOfTheChannelException extends ValidationException {
    public UserAreNotTheOwnerOfTheChannelException() {
        super();
    }

    public UserAreNotTheOwnerOfTheChannelException(String message) {
        super(message);
    }

    public UserAreNotTheOwnerOfTheChannelException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAreNotTheOwnerOfTheChannelException(Throwable cause) {
        super(cause);
    }
}
