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
public class UserMessagePopulator implements Populator<UserMessage, UserMessageData> {
    @Resource
    @Qualifier("userConverter")
    private final Converter<User, UserData> userConverter;

    @Override
    public void populate(UserMessage source, UserMessageData target) throws ConversionException {
        target.setId(source.getId());
        target.setText(source.getText());
        target.setMessageId(source.getMessageId());
        target.setUser(userConverter.convert(source.getUser()));
    }
}
