package org.telegrambot.convertio.facades.populators.impl;

import org.springframework.core.convert.ConversionException;
import org.springframework.stereotype.Component;
import org.telegrambot.convertio.core.model.files.FileCategory;
import org.telegrambot.convertio.core.model.files.FileType;
import org.telegrambot.convertio.core.services.FileTypeService;
import org.telegrambot.convertio.facades.data.files.FileCategoryData;
import org.telegrambot.convertio.facades.populators.Populator;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Component
public class FileCategoryReversePopulator implements Populator<FileCategoryData, FileCategory> {
    @Resource
    private FileTypeService fileTypeService;

    @Override
    public void populate(FileCategoryData source, FileCategory target) throws ConversionException {
        target.setId(source.getId());
        target.setName(source.getName());
//        target.setFileTypes(convertWritableTypes(source.getFileTypes()));
    }

    private Set<FileType> convertWritableTypes(Set<String> writableTypes) {
        return new HashSet<>(fileTypeService.findAllByCodeSet(writableTypes));
    }
}
