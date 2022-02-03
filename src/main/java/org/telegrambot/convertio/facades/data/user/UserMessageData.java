package org.telegrambot.convertio.facades.data.user;

import lombok.*;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserMessageData {

    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    @EqualsAndHashCode.Include
    private UserData user;

    @Getter
    @Setter
    @EqualsAndHashCode.Include
    private int messageId;

    @Getter
    @Setter
    private String text;
}
