package org.telegrambot.convertio.facades.data.user;

import lombok.*;
import org.telegrambot.convertio.occ.bot.entities.InputType;
import org.telegrambot.convertio.occ.bot.entities.State;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RequestData {

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
    private String messageText;
    @Getter
    @Setter
    private String input;
    @Getter
    @Setter
    private InputType inputType;
    @Getter
    @Setter
    private State botState;

}
