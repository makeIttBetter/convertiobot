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
public class FileCategoryPopulator implements Populator<FileCategory, FileCategoryData> {
    @Resource
    private FileTypeService fileTypeService;

    @Override
    public void populate(FileCategory source, FileCategoryData target) throws ConversionException {
        target.setId(source.getId());
        target.setName(source.getName());
        target.setFileTypes(convertWritableTypes(fileTypeService.findAllByCategory(source)));
    }

    private Set<String> convertWritableTypes(Set<FileType> writableTypes) {
        Set<String> result = new HashSet<>();
        writableTypes.forEach((type) -> result.add(type.getCode()));
        return result;
    }
}
