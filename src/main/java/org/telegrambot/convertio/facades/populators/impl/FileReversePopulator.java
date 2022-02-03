package org.telegrambot.convertio.facades.populators.impl;

import org.springframework.core.convert.ConversionException;
import org.springframework.stereotype.Component;
import org.telegrambot.convertio.core.model.files.File;
import org.telegrambot.convertio.facades.data.files.FileData;
import org.telegrambot.convertio.facades.populators.Populator;

@Component
public class FileReversePopulator implements Populator<FileData, File> {
    @Override
    public void populate(FileData source, File target) throws ConversionException {
        target.setId(source.getId());
        target.setFileId(source.getFileId());
        target.setDownloadLink(source.getDownloadLink());
    }
}
