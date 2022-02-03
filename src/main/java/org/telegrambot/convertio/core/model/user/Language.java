package org.telegrambot.convertio.core.model.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.telegrambot.convertio.consts.Commands;

import java.util.Locale;
import java.util.ResourceBundle;

@Getter
@RequiredArgsConstructor
public enum Language {
    EN(Commands.LANGUAGE_EN, ResourceBundle.getBundle
            ("resources", new Locale("en", "US"))),
    RU(Commands.LANGUAGE_RU, ResourceBundle.getBundle
            ("resources", new Locale("ru", "RU")));

    private final String name;
    private final ResourceBundle resourceBundle;

    public static int getIndexByName(String language) {
        for (int i = 0; i < values().length; i++) {
            if (language.equalsIgnoreCase(Language.values()[i].getName())) {
                return i + 1;
            }
        }
        return -1;
    }

    public static Language getStateByName(String language) {
        for (int i = 0; i < values().length; i++) {
            Language lang = Language.values()[i];
            if (language.equalsIgnoreCase(lang.getName())) {
                return lang;
            }
        }
        throw new UnsupportedOperationException();
    }

    public int getIndex() {
        for (int i = 0; i < values().length; i++) {
            if (this.equals(Language.values()[i])) {
                return i + 1;
            }
        }
        return -1;
    }

}
