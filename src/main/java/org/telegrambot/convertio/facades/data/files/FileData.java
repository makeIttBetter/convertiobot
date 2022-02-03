package org.telegrambot.convertio.facades.data.files;

import lombok.*;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FileData {

    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    @EqualsAndHashCode.Include
    private String fileId;

    @Getter
    @Setter
    private String downloadLink;
}
