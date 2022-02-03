package org.telegrambot.convertio.facades.data.files;

import lombok.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FileTypeData {

    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    @EqualsAndHashCode.Include
    private String code;

    @Getter
    @Setter
    private boolean readable;

    @Getter
    @Setter
    private boolean writeable;

    @Getter
    @Setter
    private FileCategoryData category;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private Set<File2FileData> writableTypes = Collections.synchronizedSet(new HashSet<>());

}
