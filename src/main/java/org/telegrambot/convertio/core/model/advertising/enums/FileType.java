package org.telegrambot.convertio.core.model.advertising.enums;

public enum FileType {
    PHOTO("Photo"), GIF("Gif"), VIDEO("Video"), AUDIO("Audio");

    private int id;
    private String name;

    FileType(String name) {
        this.name = name;
    }

    public static int getIntKey(String fileType) {
        for (int i = 0; i < 3; i++) {
            if (fileType.equalsIgnoreCase(FileType.values()[i].getName())) {
                return i + 1;
            }
        }
        return -1;
    }

    public int getIntKey() {
        for (int i = 0; i < 3; i++) {
            if (this.equals(FileType.values()[i])) {
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
