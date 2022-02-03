package org.telegrambot.convertio.facades.populators.impl;

import org.springframework.core.convert.ConversionException;
import org.springframework.stereotype.Component;
import org.telegrambot.convertio.core.model.files.File2File;
import org.telegrambot.convertio.facades.data.files.File2FileData;
import org.telegrambot.convertio.facades.populators.Populator;

@Component
public class File2FilePopulator implements Populator<File2File, File2FileData> {
    @Override
    public void populate(File2File source, File2FileData target) throws ConversionException {
        target.setId(source.getId());
        target.setType(source.getType().getCode());
        target.setWriteableType(source.getWriteableType().getCode());
    }
}
