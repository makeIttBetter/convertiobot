package org.telegrambot.convertio.facades.facades;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.telegrambot.convertio.core.model.files.File2File;
import org.telegrambot.convertio.core.model.files.FileType;
import org.telegrambot.convertio.core.services.File2FileService;
import org.telegrambot.convertio.exceptions.facades.*;
import org.telegrambot.convertio.exceptions.services.ServiceException;
import org.telegrambot.convertio.facades.converters.Converter;
import org.telegrambot.convertio.facades.data.files.File2FileData;
import org.telegrambot.convertio.facades.data.files.FileTypeData;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@org.springframework.stereotype.Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class File2FileFacade implements Facade<File2FileData, Long> {
    @Resource
    @Qualifier("file2FileConverter")
    private final Converter<File2File, File2FileData> converter;
    @Resource
    @Qualifier("file2FileReverseConverter")
    private final Converter<File2FileData, File2File> reverseConverter;
    @Resource
    @Qualifier("fileTypeReverseConverter")
    private final Converter<FileTypeData, FileType> fileTypeReverseConverter;
    @Resource
    private final File2FileService file2FileService;

    @Override
    public File2FileData create(File2FileData dataObject) throws FacadeException {
        try {
            File2File model = reverseConverter.convert(dataObject);
            file2FileService.create(model);
            return converter.convert(Objects.requireNonNull(model));
        } catch (Exception e) {
            throw new FacadeCreateException("Error creating entity", e);
        }
    }

    @Override
    public File2FileData update(File2FileData dataObject) throws FacadeException {
        try {
            File2File model = reverseConverter.convert(dataObject);
            file2FileService.update(model);
            return converter.convert(Objects.requireNonNull(model));
        } catch (Exception e) {
            throw new FacadeUpdateException("Error updating entity", e);
        }
    }

    @Override
    public File2FileData read(Long id) throws FacadeException {
        try {
            return converter.convert(file2FileService.read(id));
        } catch (ServiceException e) {
            throw new FacadeReadException("Entity not found");
        }
    }

    @Override
    public void delete(Long id) throws FacadeDeleteException {
        try {
            file2FileService.delete(id);
        } catch (Exception e) {
            throw new FacadeDeleteException("Error deleting entity");
        }
    }

    @Override
    public List<File2FileData> getAll() {
        return converter.convertAll(file2FileService.getAll());
    }

    @Override
    public long count() {
        return file2FileService.count();
    }

    public List<File2FileData> findAllByType(FileTypeData type) {
        FileType model = fileTypeReverseConverter.convert(type);
        return converter.convertAll(file2FileService.findAllByType(model));
    }

}
