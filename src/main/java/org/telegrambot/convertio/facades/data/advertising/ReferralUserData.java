package org.telegrambot.convertio.facades.data.advertising;


import lombok.*;
import org.telegrambot.convertio.facades.data.user.UserData;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ReferralUserData {

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
    private ReferralData referral;

}
