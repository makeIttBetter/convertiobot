package org.telegrambot.convertio.core.model.advertising;


import lombok.*;
import org.telegrambot.convertio.core.model.Model;
import org.telegrambot.convertio.core.model.user.User;

import javax.persistence.*;

@Entity
@Table(name = "referralUsers", uniqueConstraints =
        {@UniqueConstraint(columnNames = {"user", "referral"})})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ReferralUser extends Model {

    private static final long serialVersionUID = -2101469920853698554L;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "referral")
    private Referral referral;

}
