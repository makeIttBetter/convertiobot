package org.telegrambot.convertio.facades.facades;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.telegrambot.convertio.core.model.files.File;
import org.telegrambot.convertio.core.services.FileService;
import org.telegrambot.convertio.exceptions.facades.*;
import org.telegrambot.convertio.exceptions.services.ServiceException;
import org.telegrambot.convertio.facades.converters.Converter;
import org.telegrambot.convertio.facades.data.files.FileData;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@org.springframework.stereotype.Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FileFacade implements Facade<FileData, Long> {
    @Resource
    @Qualifier("fileConverter")
    private final Converter<File, FileData> converter;
    @Resource
    @Qualifier("fileReverseConverter")
    private final Converter<FileData, File> reverseConverter;
    @Resource
    private final FileService fileService;

    @Override
    public FileData create(FileData dataObject) throws FacadeException {
        try {
            File model = reverseConverter.convert(dataObject);
            fileService.create(model);
            return converter.convert(Objects.requireNonNull(model));
        } catch (Exception e) {
            throw new FacadeCreateException("Error creating entity", e);
        }
    }

    @Override
    public FileData update(FileData dataObject) throws FacadeException {
        try {
            File model = reverseConverter.convert(dataObject);
            fileService.update(model);
            return converter.convert(Objects.requireNonNull(model));
        } catch (Exception e) {
            throw new FacadeUpdateException("Error updating entity", e);
        }
    }

    @Override
    public FileData read(Long id) throws FacadeException {
        try {
            return converter.convert(fileService.read(id));
        } catch (ServiceException e) {
            throw new FacadeReadException("Entity not found");
        }
    }

    @Override
    public void delete(Long id) throws FacadeDeleteException {
        try {
            fileService.delete(id);
        } catch (Exception e) {
            throw new FacadeDeleteException("Error deleting entity");
        }
    }

    @Override
    public List<FileData> getAll() {
        return converter.convertAll(fileService.getAll());
    }

    @Override
    public long count() {
        return fileService.count();
    }

}
