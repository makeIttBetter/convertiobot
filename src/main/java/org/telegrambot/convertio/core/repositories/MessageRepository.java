package org.telegrambot.convertio.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.telegrambot.convertio.core.model.user.User;
import org.telegrambot.convertio.core.model.user.UserMessage;

import java.util.List;
import java.util.Set;

@Repository
public interface MessageRepository extends JpaRepository<UserMessage, Long> {

    List<UserMessage> findAllByUser(User user);

    void removeAllByUser(User user);

    void removeByUserAndMessageId(User user, int messageId);

    @Modifying
    @Query(value = "DELETE FROM UserMessage um" +
            " WHERE um.user = :user AND um.messageId" +
            " IN (:messList)")
    void removeByUserAndMessageId
            (@Param("user") User user,
             @Param("messList") Set<Integer> messageIdList);

    void removeByUserChatIdAndMessageId(String chatId, int messageId);
}
