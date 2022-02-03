package org.telegrambot.convertio.occ.helpers;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ErrorMessageHelper {
    private static final String prefix = "\n\n⚠️\n";
    private static final String regexToRemove = "\n\n⚠(.|\n)+";

    public String concat(String messageText, String errorText) {
        String exceptionMessage = prefix + errorText;
        return removeErrorMessage(messageText)
                .concat(exceptionMessage);
    }

    public String removeErrorMessage(String messageText) {
        return messageText.replaceAll(regexToRemove, "");
    }
}
