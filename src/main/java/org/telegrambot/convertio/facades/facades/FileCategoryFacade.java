package org.telegrambot.convertio.facades.facades;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.telegrambot.convertio.core.model.files.FileCategory;
import org.telegrambot.convertio.core.services.FileCategoryService;
import org.telegrambot.convertio.exceptions.facades.*;
import org.telegrambot.convertio.exceptions.services.ServiceException;
import org.telegrambot.convertio.facades.converters.Converter;
import org.telegrambot.convertio.facades.data.files.FileCategoryData;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@org.springframework.stereotype.Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FileCategoryFacade implements Facade<FileCategoryData, Long> {
    @Resource
    @Qualifier("fileCategoryConverter")
    private final Converter<FileCategory, FileCategoryData> converter;
    @Resource
    @Qualifier("fileCategoryReverseConverter")
    private final Converter<FileCategoryData, FileCategory> reverseConverter;
    @Resource
    private final FileCategoryService fileCategoryService;

    @Override
    public FileCategoryData create(FileCategoryData dataObject) throws FacadeException {
        try {
            FileCategory model = reverseConverter.convert(dataObject);
            fileCategoryService.create(model);
            return converter.convert(Objects.requireNonNull(model));
        } catch (Exception e) {
            throw new FacadeCreateException("Error creating entity", e);
        }
    }

    @Override
    public FileCategoryData update(FileCategoryData dataObject) throws FacadeException {
        try {
            FileCategory model = reverseConverter.convert(dataObject);
            fileCategoryService.update(model);
            return converter.convert(Objects.requireNonNull(model));
        } catch (Exception e) {
            throw new FacadeUpdateException("Error updating entity", e);
        }
    }

    @Override
    public FileCategoryData read(Long id) throws FacadeException {
        try {
            return converter.convert(fileCategoryService.read(id));
        } catch (ServiceException e) {
            throw new FacadeReadException("Entity not found");
        }
    }

    @Override
    public void delete(Long id) throws FacadeDeleteException {
        try {
            fileCategoryService.delete(id);
        } catch (Exception e) {
            throw new FacadeDeleteException("Error deleting entity");
        }
    }

    @Override
    public List<FileCategoryData> getAll() {
        return converter.convertAll(fileCategoryService.getAll());
    }

    @Override
    public long count() {
        return fileCategoryService.count();
    }

}
