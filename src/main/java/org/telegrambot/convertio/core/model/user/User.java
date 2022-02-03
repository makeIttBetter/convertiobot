package org.telegrambot.convertio.core.model.user;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.telegrambot.convertio.core.model.Model;
import org.telegrambot.convertio.occ.bot.entities.State;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class User extends Model {
    private static final long serialVersionUID = -8739786771802299999L;

    @Column(unique = true, nullable = false, length = 20)
    @EqualsAndHashCode.Include
    private String chatId;

    @Column(length = 40)
    private String userName;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false)
    private Date activityDate;

    @Column(nullable = false)
    private Date registerDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("CLIENT")
    @Builder.Default
    private Role role = Role.CLIENT;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("RU")
    @Builder.Default
    private Language language = Language.RU;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean adminBlock;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean autoBlock;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private State botState = State.NONE;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean saveHistory;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int requestAmount;

    @Column(name = "blockDate")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    private Date blockDate = new Date();

    @Column(name = "overloadPeriod")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    private Date overloadStartTime = new Date();


    public String getUserName() {
        return userName != null ? "@" + userName : String.valueOf(chatId);
    }
}
