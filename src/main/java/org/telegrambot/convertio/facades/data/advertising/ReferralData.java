package org.telegrambot.convertio.facades.data.advertising;


import lombok.*;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ReferralData {

    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    @EqualsAndHashCode.Include
    private String payload;

    @Getter
    @Setter
    private int uniqueClicks;

    @Getter
    @Setter
    private int allClicks;

    @Getter
    @Setter
    private int newUsers;

    @Getter
    @Setter
    private boolean active = true;

}
