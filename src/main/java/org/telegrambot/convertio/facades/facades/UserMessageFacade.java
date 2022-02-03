package org.telegrambot.convertio.facades.facades;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegrambot.convertio.core.model.user.User;
import org.telegrambot.convertio.core.model.user.UserMessage;
import org.telegrambot.convertio.core.services.UserMessageService;
import org.telegrambot.convertio.exceptions.facades.*;
import org.telegrambot.convertio.exceptions.services.ServiceException;
import org.telegrambot.convertio.facades.converters.Converter;
import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.facades.data.user.UserMessageData;
import org.telegrambot.convertio.occ.bot.view.message.MessageEditor;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@org.springframework.stereotype.Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserMessageFacade implements Facade<UserMessageData, Long> {
    @Resource
    @Qualifier("userMessageConverter")
    private final Converter<UserMessage, UserMessageData> converter;
    @Resource
    @Qualifier("userMessageReverseConverter")
    private final Converter<UserMessageData, UserMessage> reverseConverter;
    @Resource
    @Qualifier("userReverseConverter")
    private final Converter<UserData, User> reverseUserConverter;
    @Resource
    private final UserMessageService userMessageService;
    @Resource
    private final MessageEditor messageEditor;

    @Override
    public UserMessageData create(UserMessageData dataObject) throws FacadeException {
        try {
            UserMessage model = reverseConverter.convert(dataObject);
            userMessageService.create(model);
            return converter.convert(Objects.requireNonNull(model));
        } catch (Exception e) {
            throw new FacadeCreateException("Error creating entity", e);
        }
    }

    @Override
    public UserMessageData update(UserMessageData dataObject) throws FacadeException {
        try {
            UserMessage model = reverseConverter.convert(dataObject);
            userMessageService.update(model);
            return converter.convert(Objects.requireNonNull(model));
        } catch (Exception e) {
            throw new FacadeUpdateException("Error updating entity", e);
        }
    }

    @Override
    public UserMessageData read(Long id) throws FacadeException {
        try {
            return converter.convert(userMessageService.read(id));
        } catch (ServiceException e) {
            throw new FacadeReadException("Entity not found");
        }
    }

    @Override
    public void delete(Long id) throws FacadeDeleteException {
        try {
            userMessageService.delete(id);
        } catch (Exception e) {
            throw new FacadeDeleteException("Error deleting entity");
        }
    }

    @Override
    public List<UserMessageData> getAll() {
        return converter.convertAll(userMessageService.getAll());
    }

    @Override
    public long count() {
        return userMessageService.count();
    }

    public void deleteByUserAndMessageId(UserData user, int messageId) {
        User userModel = reverseUserConverter.convert(user);
        userMessageService.deleteByUserAndMessageId(userModel, messageId);
    }

    public void deleteByUserAndMessageId(UserData user, Set<Integer> messageId) {
        User userModel = reverseUserConverter.convert(user);
        userMessageService.deleteByUserAndMessageId(userModel, messageId);
    }

    public void deleteByUserChatIdAndMessageId(String chatId, int messageId) {
        userMessageService.deleteByUserChatIdAndMessageId(chatId, messageId);
    }


    public void deleteAllByUser(UserData user) {
        User model = reverseUserConverter.convert(user);
        userMessageService.deleteAllByUser(model);
    }

    public List<UserMessageData> findAllByUser(UserData user) {
        User model = reverseUserConverter.convert(user);
        List<UserMessage> foundMessages = userMessageService.findAllByUser(model);
        return converter.convertAll(foundMessages);
    }

    public Set<PartialBotApiMethod<? extends Serializable>> hidePreviousReplyMarkup(UserData user) {
        Set<PartialBotApiMethod<? extends Serializable>> apiMethods = new HashSet<>();
        // check if User has messages with inlineMarkup
        List<UserMessageData> messages = findAllByUser(user);
        for (UserMessageData userMessage : messages) {
            EditMessageReplyMarkup editMessageText = messageEditor.getEditMessageReplyMarkup(user.getChatId(), userMessage.getMessageId());
            apiMethods.add(editMessageText);
        }
        return apiMethods;
    }

    public void hidePreviousReplyMarkupImmediately(UserData user, TelegramLongPollingBot bot) {
        // check if User has messages with
        List<UserMessageData> messages = findAllByUser(user);
        for (UserMessageData userMessage : messages) {
            EditMessageReplyMarkup editMessageText = messageEditor.getEditMessageReplyMarkup(user.getChatId(), userMessage.getMessageId());
            try {
                bot.execute(editMessageText);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

}
