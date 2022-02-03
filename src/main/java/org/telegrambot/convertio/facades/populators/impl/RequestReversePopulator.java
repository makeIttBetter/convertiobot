package org.telegrambot.convertio.facades.populators.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionException;
import org.springframework.stereotype.Component;
import org.telegrambot.convertio.core.model.user.Request;
import org.telegrambot.convertio.core.model.user.User;
import org.telegrambot.convertio.facades.converters.Converter;
import org.telegrambot.convertio.facades.data.user.RequestData;
import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.facades.populators.Populator;

import javax.annotation.Resource;

@Component
@RequiredArgsConstructor
public class RequestReversePopulator implements Populator<RequestData, Request> {
    @Resource
    @Qualifier("userReverseConverter")
    private final Converter<UserData, User> userReverseConverter;

    @Override
    public void populate(RequestData source, Request target) throws ConversionException {
        target.setId(source.getId());
        target.setInput(source.getInput());
        target.setBotState(source.getBotState());
        target.setMessageId(source.getMessageId());
        target.setInputType(source.getInputType());
        target.setMessageText(source.getMessageText());
        target.setUser(userReverseConverter.convert(source.getUser()));
    }
}
