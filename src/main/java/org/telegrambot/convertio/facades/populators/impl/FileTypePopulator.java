package org.telegrambot.convertio.facades.populators.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionException;
import org.springframework.stereotype.Component;
import org.telegrambot.convertio.core.model.files.File2File;
import org.telegrambot.convertio.core.model.files.FileCategory;
import org.telegrambot.convertio.core.model.files.FileType;
import org.telegrambot.convertio.core.services.File2FileService;
import org.telegrambot.convertio.facades.converters.Converter;
import org.telegrambot.convertio.facades.data.files.File2FileData;
import org.telegrambot.convertio.facades.data.files.FileCategoryData;
import org.telegrambot.convertio.facades.data.files.FileTypeData;
import org.telegrambot.convertio.facades.populators.Populator;

import javax.annotation.Resource;
import java.util.HashSet;

@Component
public class FileTypePopulator implements Populator<FileType, FileTypeData> {
    @Resource
    @Qualifier("fileCategoryConverter")
    private Converter<FileCategory, FileCategoryData> categoryConverter;
    @Resource
    @Qualifier("file2FileConverter")
    private Converter<File2File, File2FileData> file2FileConverter;
    @Resource
    private File2FileService file2FileService;

    @Override
    public void populate(FileType source, FileTypeData target) throws ConversionException {
        target.setId(source.getId());
        target.setCode(source.getCode());
        target.setDescription(source.getDescription());
        target.setReadable(source.isReadable());
        target.setWriteable(source.isWriteable());
        target.setCategory(categoryConverter.convert(source.getCategory()));
        target.setWritableTypes(new HashSet<>(file2FileConverter.convertAll(file2FileService.findAllByType(source))));
    }
}
