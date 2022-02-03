package org.telegrambot.convertio.facades.populators.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionException;
import org.springframework.stereotype.Component;
import org.telegrambot.convertio.core.model.files.FileCategory;
import org.telegrambot.convertio.core.model.files.FileType;
import org.telegrambot.convertio.facades.converters.Converter;
import org.telegrambot.convertio.facades.data.files.FileCategoryData;
import org.telegrambot.convertio.facades.data.files.FileTypeData;
import org.telegrambot.convertio.facades.populators.Populator;

import javax.annotation.Resource;

@Component
public class FileTypeReversePopulator implements Populator<FileTypeData, FileType> {
    @Resource
    @Qualifier("fileCategoryReverseConverter")
    private Converter<FileCategoryData, FileCategory> categoryReverseConverter;

    @Override
    public void populate(FileTypeData source, FileType target) throws ConversionException {
        target.setId(source.getId());
        target.setCode(source.getCode());
        target.setDescription(source.getDescription());
        target.setReadable(source.isReadable());
        target.setWriteable(source.isWriteable());
        target.setCategory(categoryReverseConverter.convert(source.getCategory()));
    }
}
