package org.telegrambot.convertio.occ.helpers.validators;

import org.telegrambot.convertio.exceptions.validators.ValidationException;

public interface Validator {
    void validate(ValidationEntity validationEntity) throws ValidationException;
}
