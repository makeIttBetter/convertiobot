package org.telegrambot.convertio.core.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.telegrambot.convertio.core.model.user.Role;
import org.telegrambot.convertio.core.model.user.User;
import org.telegrambot.convertio.core.repositories.UserRepository;
import org.telegrambot.convertio.exceptions.services.ObjectNotFoundException;
import org.telegrambot.convertio.exceptions.services.ServiceException;
import org.telegrambot.convertio.occ.bot.entities.State;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;


@Slf4j
@Transactional
@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserService implements Service<User, Long> {
    @Resource
    private final UserRepository userRepository;
    @Value("${userUpdated}")
    private String userUpdated;
    @Value("${userCreated}")
    private String userCreated;


    @Override
    public User create(User user) {
        log.info(userCreated);

        user = userRepository.save(user);
        return user;
    }

    /**
     * Finds all users who has sent more than
     * 1 requests per 1 second to the bot
     * during 30 minutes
     */
    public Set<User> findViolators() {
        return userRepository.findViolators();
    }

    /**
     * set autoBlock to true for all users from the received list
     *
     * @param violators list of violators who need to be blocked
     */
    public void blockByList(Set<User> violators) {
        userRepository.blockByList(violators);
    }

    /**
     * Blocks all users who has sent more than
     * 1 requests per 1 second to the bot
     * during 30 minutes
     */
    public void blockViolators() {
        userRepository.blockViolators();
    }

    /**
     * Unblocks all whose block time has expired.
     * Unblocks all users after been blocked for 6 hours
     */
    public void unblockAllNecessary() {
        userRepository.unblockAllNecessary();
    }

    /**
     * Sets amount of request of each user to the bot to 0
     */
    public void discardRequestAmount() {
        userRepository.discardRequestAmount();
    }


    @Override

    public User update(User user) {
        log.info(userUpdated);
        return userRepository.save(user);
    }

    public void updateUserState(User user, State state) {
        // updating user in DB
        user.setBotState(state);
        update(user);
    }

    @Override
    public User read(Long id) throws ServiceException {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ObjectNotFoundException("Entity not found");
        }
        return user;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    public User findByChatId(String chatId) throws ServiceException {
        User user = userRepository.findByChatId(chatId);
        if (user == null) {
            throw new ObjectNotFoundException("Entity not found");
        }
        return user;
    }

    @Transactional
    public void userBlockTransactionRun() {
        unblockAllNecessary();
        blockViolators();
        discardRequestAmount();
    }

    public User getOwner() {
        return userRepository.findFirstByRole(Role.OWNER);
    }

}
