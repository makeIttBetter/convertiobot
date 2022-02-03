package org.telegrambot.convertio.facades.data.advertising;

import lombok.*;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ButtonData {

    @Getter
    @Setter
    @EqualsAndHashCode.Include
    private long id;

    @Getter
    @Setter
    private String text;

    @Getter
    @Setter
    private String callback;

    @Getter
    @Setter
    private String link;

    @Getter
    @Setter
    private AdvertisingData advertising;
}
