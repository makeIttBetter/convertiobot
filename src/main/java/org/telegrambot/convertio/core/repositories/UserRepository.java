package org.telegrambot.convertio.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.telegrambot.convertio.core.model.user.Role;
import org.telegrambot.convertio.core.model.user.User;

import java.util.Date;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT count(u) FROM User u WHERE u.registerDate BETWEEN ?1 and ?2")
    Integer getNewUsersByPeriod(Date fromDate, Date toDate);

    @Query(value = "SELECT count(u) FROM User u WHERE u.activityDate BETWEEN ?1 and ?2")
    Integer getActiveUsersByPeriod(Date fromDate, Date toDate);

    User findByChatId(String chatId);

    @Query(value = "select * from users where requestAmount > 600 " +
            " AND activityDate > date_sub(activityDate, INTERVAL 10 MINUTE)",
            nativeQuery = true)
    Set<User> findViolators();

    @Modifying
    @Query(value = "update users set autoBlock = true where requestAmount > 600 " +
            " AND activityDate > date_sub(activityDate, INTERVAL 10 MINUTE)",
            nativeQuery = true)
    void blockViolators();

    @Modifying
    @Query(value = "update User set requestAmount = 0 where id is not null")
    void discardRequestAmount();

    @Modifying
    @Query(value = "update User set autoBlock = true where id in :users")
    void blockByList(@Param("users") Set<User> users);

    @Modifying
    @Query(value = "update users set autoBlock = false" +
            " where activityDate < date_sub(activityDate, INTERVAL 3 HOUR)",
            nativeQuery = true)
    void unblockAllNecessary();

    User findFirstByRole(Role role);

}
