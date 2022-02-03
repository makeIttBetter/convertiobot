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
public class FileCategoryData {

    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    @EqualsAndHashCode.Include
    private String name;

    @Getter
    @Setter
    private Set<String> fileTypes = Collections.synchronizedSet(new HashSet<>());

}
