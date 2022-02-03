package org.telegrambot.convertio.occ.helpers.validators;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegrambot.convertio.exceptions.validators.HtmlMarkupException;
import org.telegrambot.convertio.exceptions.validators.ValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@Component
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HtmlMarkupValidator implements Validator {
    @Value("${incorrectHtmlMurkUpException}")
    @Setter
    private String incorrectHtmlMurkUpException;

    @Override
    public void validate(ValidationEntity validationEntity) throws ValidationException {
        String input = validationEntity.getInput()
                .replaceAll("<(\\w)>((.|\\n)+)</\\1>", "$2");

        Pattern pattern = Pattern.compile("[<>]");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            ValidationException e = new HtmlMarkupException(incorrectHtmlMurkUpException);
            log.error(incorrectHtmlMurkUpException, e);
            throw e;
        }

    }

}


