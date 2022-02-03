package org.telegrambot.convertio.facades.data.advertising;

import lombok.*;
import org.telegrambot.convertio.core.model.advertising.enums.FileType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AdvertisingData {

    @Getter
    @Setter
    @EqualsAndHashCode.Include
    private long id;

    @Getter
    @Setter
    @EqualsAndHashCode.Include
    private FileType fileType;

    @Getter
    @Setter
    @EqualsAndHashCode.Include
    private String text;

    @Getter
    @Setter
    private String fileId;

    @Getter
    @Setter
    private List<Long> buttons = Collections.synchronizedList(new ArrayList<>());


}
