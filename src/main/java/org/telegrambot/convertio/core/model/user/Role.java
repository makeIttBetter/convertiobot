package org.telegrambot.convertio.core.model.user;

public enum Role {
    ADMIN("Admin"), CLIENT("Client"), OWNER("Owner");

    private int id;
    private String name;

    Role(String name) {
        this.name = name;
    }

    public static int getIntKey(String role) {
        for (int i = 0; i < Role.values().length; i++) {
            if (role.equalsIgnoreCase(Role.values()[i].getName())) {
                return i + 1;
            }
        }
        return -1;
    }

    public int getIntKey() {
        for (int i = 0; i < Role.values().length; i++) {
            if (this.equals(Role.values()[i])) {
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
