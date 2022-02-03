package org.telegrambot.convertio.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.telegrambot.convertio.core.model.user.Request;
import org.telegrambot.convertio.core.model.user.User;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query(value = "select * from requests where user = :user order by id desc limit 2",
            nativeQuery = true)
    List<Request> findTwoLastByUserOrderByIdDesc(@Param("user") User user);

    @Query(value = "select * from requests where user = :user order by id desc limit 1",
            nativeQuery = true)
    Request findLastByUser(@Param("user") User user);

    @Modifying
    void deleteAllByUser(User user);
}
