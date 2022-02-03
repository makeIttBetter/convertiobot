package org.telegrambot.convertio.core.model.advertising.enums;

public enum Format {
    INDEPENDENT("Independent"), WITH_REQUEST("With request"),
    WITH_STATISTICS("With statistics"), WITH_REQUEST_MINI("With request mini"),
    REFERRALS("Referrals"), HOLIDAY("Holiday");

    private int id;
    private String name;

    Format(String name) {
        this.name = name;
    }

    public static int getIntKey(String format) {
        int size = Format.values().length;
        for (int i = 0; i < size; i++) {
            if (format.equalsIgnoreCase(Format.values()[i].getName())) {
                return i + 1;
            }
        }
        return -1;
    }

    public int getIntKey() {
        int size = Format.values().length;
        for (int i = 0; i < size; i++) {
            if (this.equals(Format.values()[i])) {
                return i + 1;
            }
        }
        return -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
