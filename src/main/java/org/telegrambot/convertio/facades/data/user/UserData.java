package org.telegrambot.convertio.facades.data.user;

import lombok.*;
import org.telegrambot.convertio.core.model.user.Language;
import org.telegrambot.convertio.core.model.user.Role;
import org.telegrambot.convertio.occ.bot.entities.State;

import java.util.Date;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserData {

    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    @EqualsAndHashCode.Include
    private String chatId;

    @Getter
    @Setter
    private String userName;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Date activityDate;

    @Getter
    @Setter
    private Date registerDate;

    @Getter
    @Setter
    private Role role;

    @Getter
    @Setter
    private Language language;

    @Getter
    @Setter
    private boolean adminBlock;

    @Getter
    @Setter
    private boolean autoBlock;

    @Getter
    @Setter
    private State botState = State.NONE;

    @Getter
    @Setter
    private boolean saveHistory;

    @Getter
    @Setter
    private int requestAmount;

    @Getter
    @Setter
    private Date blockDate = new Date();

    @Getter
    @Setter
    private Date overloadStartTime = new Date();

}
