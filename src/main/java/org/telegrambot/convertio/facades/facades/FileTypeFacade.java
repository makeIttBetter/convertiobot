package org.telegrambot.convertio.facades.facades;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.telegrambot.convertio.core.model.files.File2File;
import org.telegrambot.convertio.core.model.files.FileType;
import org.telegrambot.convertio.core.services.File2FileService;
import org.telegrambot.convertio.core.services.FileTypeService;
import org.telegrambot.convertio.exceptions.facades.*;
import org.telegrambot.convertio.exceptions.services.ServiceException;
import org.telegrambot.convertio.facades.converters.Converter;
import org.telegrambot.convertio.facades.data.files.File2FileData;
import org.telegrambot.convertio.facades.data.files.FileTypeData;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@org.springframework.stereotype.Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FileTypeFacade implements Facade<FileTypeData, Long> {
    @Resource
    @Qualifier("fileTypeConverter")
    private final Converter<FileType, FileTypeData> converter;
    @Resource
    @Qualifier("fileTypeReverseConverter")
    private final Converter<FileTypeData, FileType> reverseConverter;
    @Resource
    @Qualifier("file2FileReverseConverter")
    private final Converter<File2FileData, File2File> file2FileReverseConverter;
    @Resource
    private final FileTypeService fileTypeService;
    @Resource
    private final File2FileService file2FileService;

    @Override
    public FileTypeData create(FileTypeData dataObject) throws FacadeException {
        try {
            FileType model = reverseConverter.convert(dataObject);
            List<File2File> file2FileModels = file2FileReverseConverter.convertAll(dataObject.getWritableTypes());
            fileTypeService.create(model);
            file2FileService.createAll(file2FileModels);
            return converter.convert(Objects.requireNonNull(model));
        } catch (Exception e) {
            throw new FacadeCreateException("Error creating entity", e);
        }
    }

    @Override
    public FileTypeData update(FileTypeData dataObject) throws FacadeException {
        try {
            FileType model = reverseConverter.convert(dataObject);
            List<File2File> file2FileModels = file2FileReverseConverter.convertAll(dataObject.getWritableTypes());
            fileTypeService.update(model);
            file2FileService.updateAll(file2FileModels);
            return converter.convert(Objects.requireNonNull(model));
        } catch (Exception e) {
            throw new FacadeUpdateException("Error updating entity", e);
        }
    }

    @Override
    public FileTypeData read(Long id) throws FacadeException {
        try {
            return converter.convert(fileTypeService.read(id));
        } catch (ServiceException e) {
            throw new FacadeReadException("Entity not found");
        }
    }

    @Override
    public void delete(Long id) throws FacadeDeleteException {
        try {
            fileTypeService.delete(id);
        } catch (Exception e) {
            throw new FacadeDeleteException("Error deleting entity");
        }
    }

    @Override
    public List<FileTypeData> getAll() {
        return converter.convertAll(fileTypeService.getAll());
    }

    @Override
    public long count() {
        return fileTypeService.count();
    }

    public List<FileTypeData> findAllByCodes(Set<String> typeCodes) {
        return converter.convertAll(fileTypeService.findAllByCodeSet(typeCodes));
    }

    public FileTypeData readByCode(String code) throws FacadeReadException {
        try {
            return converter.convert(fileTypeService.readByCode(code));
        } catch (ServiceException e) {
            throw new FacadeReadException("Entity not found");
        }
    }

}
