package org.telegrambot.convertio.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.telegrambot.convertio.core.model.advertising.Advertising;

@Repository
public interface AdvertisingRepository extends JpaRepository<Advertising, Long> {

}
