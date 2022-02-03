package org.telegrambot.convertio.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.telegrambot.convertio.core.model.advertising.Referral;

@Repository
public interface ReferralRepository extends JpaRepository<Referral, Long> {

    Referral findFirstByPayload(String payload);
}
