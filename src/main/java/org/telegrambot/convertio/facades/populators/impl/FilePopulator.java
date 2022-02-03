package org.telegrambot.convertio.facades.populators.impl;

import org.springframework.core.convert.ConversionException;
import org.springframework.stereotype.Component;
import org.telegrambot.convertio.core.model.files.File;
import org.telegrambot.convertio.facades.data.files.FileData;
import org.telegrambot.convertio.facades.populators.Populator;

@Component
public class FilePopulator implements Populator<File, FileData> {
    @Override
    public void populate(File source, FileData target) throws ConversionException {
        target.setId(source.getId());
        target.setFileId(source.getFileId());
        target.setDownloadLink(source.getDownloadLink());
    }
}
