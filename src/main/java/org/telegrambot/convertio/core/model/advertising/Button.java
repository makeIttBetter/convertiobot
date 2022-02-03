package org.telegrambot.convertio.core.model.advertising;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.telegrambot.convertio.core.model.Model;

import javax.persistence.*;

@Entity
@Table(name = "buttons")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Button extends Model {

    private static final long serialVersionUID = -6673390790280196426L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertising")
    @EqualsAndHashCode.Include
    private Advertising advertising;

    @Column(length = 40)
    @EqualsAndHashCode.Include
    private String text;

    @Column(length = 45)
    private String callback;

    @Column(length = 1000)
    private String link;
}
