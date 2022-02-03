package org.telegrambot.convertio.facades.populators.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionException;
import org.springframework.stereotype.Component;
import org.telegrambot.convertio.core.model.user.User;
import org.telegrambot.convertio.core.model.user.UserMessage;
import org.telegrambot.convertio.facades.converters.Converter;
import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.facades.data.user.UserMessageData;
import org.telegrambot.convertio.facades.populators.Populator;

import javax.annotation.Resource;

@Component
@RequiredArgsConstructor
public class UserMessageReversePopulator implements Populator<UserMessageData, UserMessage> {
    @Resource
    @Qualifier("userReverseConverter")
    private final Converter<UserData, User> userReverseConverter;

    @Override
    public void populate(UserMessageData source, UserMessage target) throws ConversionException {
        target.setId(source.getId());
        target.setText(source.getText());
        target.setMessageId(source.getMessageId());
        target.setUser(userReverseConverter.convert(source.getUser()));
    }
}
