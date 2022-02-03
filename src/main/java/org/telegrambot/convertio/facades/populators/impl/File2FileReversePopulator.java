package org.telegrambot.convertio.facades.populators.impl;

import org.springframework.core.convert.ConversionException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Component;
import org.telegrambot.convertio.core.model.files.File2File;
import org.telegrambot.convertio.core.services.FileTypeService;
import org.telegrambot.convertio.exceptions.services.ServiceException;
import org.telegrambot.convertio.facades.data.files.File2FileData;
import org.telegrambot.convertio.facades.populators.Populator;

import javax.annotation.Resource;

@Component
public class File2FileReversePopulator implements Populator<File2FileData, File2File> {
    @Resource
    private FileTypeService fileTypeService;

    @Override
    public void populate(File2FileData source, File2File target) throws ConversionException {
        try {
            target.setId(source.getId());
            target.setType(fileTypeService.readByCode(source.getType()));
            target.setWriteableType(fileTypeService.readByCode(source.getWriteableType()));
        } catch (ServiceException e) {
            throw new ConversionFailedException
                    (TypeDescriptor.valueOf(File2FileData.class),
                            TypeDescriptor.valueOf(File2File.class), source, e);
        }
    }
}
