package org.telegrambot.convertio.core.model.advertising;


import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.telegrambot.convertio.core.model.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "referrals")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Referral extends Model {

    private static final long serialVersionUID = -325935545649620349L;

    @Column(unique = true, nullable = false, length = 64)
    @EqualsAndHashCode.Include
    private String payload;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int uniqueClicks;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int allClicks;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int newUsers;

    @Column(nullable = false)
    @ColumnDefault("true")
    @Builder.Default
    private boolean active = true;

}
