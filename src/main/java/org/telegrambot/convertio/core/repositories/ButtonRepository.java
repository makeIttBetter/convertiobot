package org.telegrambot.convertio.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.telegrambot.convertio.core.model.advertising.Button;

import java.util.List;

@Repository
public interface ButtonRepository extends JpaRepository<Button, Long> {

    List<Button> findAllByIdIn(List<Long> typeCodes);

}