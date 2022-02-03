package org.telegrambot.convertio.facades.facades;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.telegrambot.convertio.core.model.user.User;
import org.telegrambot.convertio.core.services.UserService;
import org.telegrambot.convertio.exceptions.facades.*;
import org.telegrambot.convertio.exceptions.services.ObjectNotFoundException;
import org.telegrambot.convertio.exceptions.services.ServiceException;
import org.telegrambot.convertio.facades.converters.Converter;
import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.occ.bot.entities.State;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.*;

@org.springframework.stereotype.Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserFacade implements Facade<UserData, Long> {
    @Resource
    @Qualifier("userConverter")
    private final Converter<User, UserData> converter;
    @Resource
    @Qualifier("userReverseConverter")
    private final Converter<UserData, User> reverseConverter;
    @Resource
    private final UserService userService;

    @Override
    public UserData create(UserData dataObject) throws FacadeException {
        try {
            User model = reverseConverter.convert(dataObject);
            userService.create(model);
            return converter.convert(Objects.requireNonNull(model));
        } catch (Exception e) {
            throw new FacadeCreateException("Error creating entity", e);
        }
    }

    @Override
    public UserData update(UserData dataObject) throws FacadeException {
        try {
            User model = reverseConverter.convert(dataObject);
            userService.update(model);
            return converter.convert(Objects.requireNonNull(model));
        } catch (Exception e) {
            throw new FacadeUpdateException("Error updating entity", e);
        }
    }

    public UserData update(String chatId, UserData dataObject) throws FacadeException {
        try {
            User foundUser = userService.findByChatId(chatId);
            User model = reverseConverter.convert(dataObject);
            Objects.requireNonNull(model).setId(foundUser.getId());
            userService.update(model);
            return converter.convert(model);
        } catch (Exception e) {
            throw new FacadeUpdateException("Error updating entity", e);
        }
    }

    @Override
    public UserData read(Long id) throws FacadeException {
        try {
            return converter.convert(userService.read(id));
        } catch (ServiceException e) {
            throw new FacadeReadException("Entity not found");
        }
    }

    @Override
    public void delete(Long id) throws FacadeDeleteException {
        try {
            userService.delete(id);
        } catch (Exception e) {
            throw new FacadeDeleteException("Error deleting entity");
        }
    }

    @Override
    public List<UserData> getAll() {
        return converter.convertAll(userService.getAll());
    }

    @Override
    public long count() {
        return userService.count();
    }

    /**
     * Finds all users who has sent more than
     * 1 requests per 1 second to the bot
     * during 30 minutes
     */
    public Set<UserData> findViolators() {
        final Set<UserData> result = Collections.synchronizedSet(new LinkedHashSet<>());
        result.addAll(converter.convertAll(userService.findViolators()));
        return result;
    }

    /**
     * set autoBlock to true for all users from the received list
     *
     * @param violators list of violators who need to be blocked
     */
    public void blockByList(Set<UserData> violators) {
        Set<User> users = Collections.synchronizedSet(new LinkedHashSet<>());
        users.addAll(reverseConverter.convertAll(violators));
        userService.blockByList(users);
    }

    /**
     * Blocks all users who has sent more than
     * 1 requests per 1 second to the bot
     * during 30 minutes
     */
    public void blockViolators() {
        userService.blockViolators();
    }

    /**
     * Unblocks all whose block time has expired.
     * Unblocks all users after been blocked for 6 hours
     */
    public void unblockAllNecessary() {
        userService.unblockAllNecessary();
    }

    /**
     * Sets amount of request of each user to the bot to 0
     */
    public void discardRequestAmount() {
        userService.discardRequestAmount();
    }

    public void updateUserState(UserData user, State state) throws FacadeException {
        user.setBotState(state);
        update(user);
    }

    public UserData findByChatId(String chatId) throws ServiceException {
        User user = userService.findByChatId(chatId);
        if (user == null) {
            throw new ObjectNotFoundException("Entity not found");
        }
        return converter.convert(user);
    }

    @Transactional
    public void userBlockTransactionRun() {
        unblockAllNecessary();
        blockViolators();
        discardRequestAmount();
    }

    public UserData getOwner() {
        return converter.convert(userService.getOwner());
    }

}
