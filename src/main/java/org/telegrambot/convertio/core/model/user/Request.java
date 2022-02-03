package org.telegrambot.convertio.core.model.user;

import lombok.*;
import org.telegrambot.convertio.core.model.Model;
import org.telegrambot.convertio.occ.bot.entities.InputType;
import org.telegrambot.convertio.occ.bot.entities.State;

import javax.persistence.*;

@Entity
@Table(name = "requests", uniqueConstraints =
        {@UniqueConstraint(columnNames = {"user", "messageId", "inputType"})})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Request extends Model {

    private static final long serialVersionUID = -3200572960649002636L;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user", nullable = false)
    @EqualsAndHashCode.Include
    private User user;
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private int messageId;
    @Column(nullable = false)
    private String messageText;
    @Column(nullable = false)
    private String input;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InputType inputType;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private State botState;

}
