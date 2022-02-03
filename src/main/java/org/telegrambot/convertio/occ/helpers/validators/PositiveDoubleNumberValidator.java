package org.telegrambot.convertio.occ.helpers.validators;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegrambot.convertio.exceptions.validators.NegativeNumberException;
import org.telegrambot.convertio.exceptions.validators.ValidationException;

@Slf4j
@Component
public class PositiveDoubleNumberValidator implements Validator {
    @Value("${negativeNumberException}")
    @Setter
    private String negativeNumberException;

    @Override
    public void validate(ValidationEntity validationEntity) throws ValidationException {
        try {
            double cost = Double.parseDouble(validationEntity.getInput());
            if (cost < 0) {
                ValidationException e = new NegativeNumberException(negativeNumberException);
                log.error(negativeNumberException, e);
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

