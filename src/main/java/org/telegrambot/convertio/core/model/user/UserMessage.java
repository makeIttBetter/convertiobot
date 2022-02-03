package org.telegrambot.convertio.core.model.user;

import lombok.*;
import org.telegrambot.convertio.core.model.Model;

import javax.persistence.*;

@Entity
@Table(name = "messages", uniqueConstraints =
        {@UniqueConstraint(columnNames = {"user", "messageId"})})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class UserMessage extends Model {

    private static final long serialVersionUID = -2773023141236146580L;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user", nullable = false)
    @EqualsAndHashCode.Include
    private User user;
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private int messageId;
    @Column(nullable = false)
    private String text;

}
