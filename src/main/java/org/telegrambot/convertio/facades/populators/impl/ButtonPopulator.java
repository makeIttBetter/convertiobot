package org.telegrambot.convertio.facades.populators.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionException;
import org.springframework.stereotype.Component;
import org.telegrambot.convertio.core.model.advertising.Advertising;
import org.telegrambot.convertio.core.model.advertising.Button;
import org.telegrambot.convertio.facades.converters.Converter;
import org.telegrambot.convertio.facades.data.advertising.AdvertisingData;
import org.telegrambot.convertio.facades.data.advertising.ButtonData;
import org.telegrambot.convertio.facades.populators.Populator;

import javax.annotation.Resource;

@Component
public class ButtonPopulator implements Populator<Button, ButtonData> {
    @Resource
    @Qualifier("advertisingConverter")
    private Converter<Advertising, AdvertisingData> advertisingConverter;

    @Override
    public void populate(Button source, ButtonData target) throws ConversionException {
        target.setId(source.getId());
        target.setText(source.getText());
        target.setAdvertising(advertisingConverter.convert(source.getAdvertising()));
        target.setCallback(source.getCallback());
        target.setLink(source.getLink());
    }
}
