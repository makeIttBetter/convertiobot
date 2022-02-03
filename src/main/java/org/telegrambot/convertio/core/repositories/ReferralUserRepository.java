package org.telegrambot.convertio.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.telegrambot.convertio.core.model.advertising.Referral;
import org.telegrambot.convertio.core.model.advertising.ReferralUser;
import org.telegrambot.convertio.core.model.user.User;

import java.util.List;

@Repository
public interface ReferralUserRepository extends JpaRepository<ReferralUser, Long> {
    List<ReferralUser> findAllByUser(User user);

    List<ReferralUser> findAllByUserId(Long userId);

    List<ReferralUser> findAllByReferral(Referral referral);

    List<ReferralUser> findAllByReferralId(Long referralId);

    ReferralUser findFirstByUserAndReferral(User user, Referral referral);

}
