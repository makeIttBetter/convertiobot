package org.telegrambot.convertio.facades.data.files;

import lombok.*;

@ToString
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class File2FileData {

    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    @EqualsAndHashCode.Include
    private String writeableType;

    public File2FileData(String type, String writeableType) {
        this.id = 0;
        this.type = type;
        this.writeableType = writeableType;
    }
}