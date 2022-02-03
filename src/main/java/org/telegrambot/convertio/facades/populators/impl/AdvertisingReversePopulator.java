package org.telegrambot.convertio.facades.populators.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionException;
import org.springframework.stereotype.Component;
import org.telegrambot.convertio.core.model.advertising.Advertising;
import org.telegrambot.convertio.core.model.advertising.Button;
import org.telegrambot.convertio.core.services.ButtonService;
import org.telegrambot.convertio.facades.data.advertising.AdvertisingData;
import org.telegrambot.convertio.facades.populators.Populator;

import javax.annotation.Resource;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AdvertisingReversePopulator implements Populator<AdvertisingData, Advertising> {
    @Resource
    private ButtonService buttonService;

    @Override
    public void populate(AdvertisingData source, Advertising target) throws ConversionException {
        target.setId(source.getId());
        target.setFileType(source.getFileType());
        target.setText(source.getText());
        target.setFileId(source.getFileId());
        target.setButtons(convertWritableTypes(source.getButtons()));
    }

    private List<Button> convertWritableTypes(List<Long> buttonIds) {
        return buttonService.findAllByIds(buttonIds);
    }
}
