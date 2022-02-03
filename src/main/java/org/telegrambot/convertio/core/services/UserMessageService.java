package org.telegrambot.convertio.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;
import org.telegrambot.convertio.core.model.user.User;
import org.telegrambot.convertio.core.model.user.UserMessage;
import org.telegrambot.convertio.core.repositories.MessageRepository;
import org.telegrambot.convertio.exceptions.services.ObjectNotFoundException;
import org.telegrambot.convertio.exceptions.services.ServiceException;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Transactional
@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserMessageService implements Service<UserMessage, Long> {
    @Resource
    private final MessageRepository messageRepository;

    @Override
    public UserMessage create(UserMessage userMessage) {
        return messageRepository.save(userMessage);
    }

    @Override
    public UserMessage update(UserMessage userMessage) {
        return messageRepository.save(userMessage);
    }

    @Override
    public UserMessage read(Long id) throws ServiceException {
        UserMessage message = messageRepository.findById(id).orElse(null);
        if (message == null) {
            throw new ObjectNotFoundException("Entity not found");
        }
        return message;
    }

    @Override
    public void delete(Long id) {
        messageRepository.deleteById(id);
    }

    public void deleteByUserAndMessageId(User user, int messageId) {
        messageRepository.removeByUserAndMessageId(user, messageId);
    }

    public void deleteByUserAndMessageId(User user, Set<Integer> messageId) {
        messageRepository.removeByUserAndMessageId(user, messageId);
    }

    public void deleteByUserChatIdAndMessageId(String chatId, int messageId) {
        messageRepository.removeByUserChatIdAndMessageId(chatId, messageId);
    }

    @Override
    public List<UserMessage> getAll() {
        return messageRepository.findAll();
    }

    @Override
    public long count() {
        return messageRepository.count();
    }

    public List<UserMessage> findAllByUser(User user) {
        return messageRepository.findAllByUser(user);
    }

    public void deleteAllByUser(User user) {
        messageRepository.removeAllByUser(user);
    }

}
