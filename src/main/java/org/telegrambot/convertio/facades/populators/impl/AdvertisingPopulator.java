package org.telegrambot.convertio.facades.populators.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionException;
import org.springframework.stereotype.Component;
import org.telegrambot.convertio.core.model.advertising.Advertising;
import org.telegrambot.convertio.core.model.advertising.Button;
import org.telegrambot.convertio.facades.data.advertising.AdvertisingData;
import org.telegrambot.convertio.facades.populators.Populator;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AdvertisingPopulator implements Populator<Advertising, AdvertisingData> {
    @Override
    public void populate(Advertising source, AdvertisingData target) throws ConversionException {
        target.setId(source.getId());
        target.setFileType(source.getFileType());
        target.setText(source.getText());
        target.setFileId(source.getFileId());
        target.setButtons(convertWritableTypes(source.getButtons()));
    }

    private List<Long> convertWritableTypes(List<Button> writableTypes) {
        List<Long> result = new ArrayList<>();
        writableTypes.forEach((type) -> result.add(type.getId()));
        return result;
    }
}
