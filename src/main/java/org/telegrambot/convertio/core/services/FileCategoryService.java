package org.telegrambot.convertio.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.telegrambot.convertio.core.model.files.FileCategory;
import org.telegrambot.convertio.core.repositories.FileCategoryRepository;
import org.telegrambot.convertio.exceptions.services.ObjectNotFoundException;
import org.telegrambot.convertio.exceptions.services.ServiceException;

import javax.annotation.Resource;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FileCategoryService implements Service<FileCategory, Long> {
    @Resource
    private final FileCategoryRepository fileCategoryRepository;

    @Override
    public FileCategory create(FileCategory fileCategory) {
        return fileCategoryRepository.save(fileCategory);
    }

    @Override
    public FileCategory update(FileCategory fileCategory) {
        return fileCategoryRepository.save(fileCategory);
    }

    @Override
    public FileCategory read(Long id) throws ServiceException {
        FileCategory fileCategory = fileCategoryRepository.findById(id).orElse(null);
        if (fileCategory == null) {
            throw new ObjectNotFoundException("Entity not found");
        }
        return fileCategory;
    }

    @Override
    public void delete(Long id) {
        fileCategoryRepository.deleteById(id);
    }

    @Override
    public List<FileCategory> getAll() {
        return fileCategoryRepository.findAll();
    }

    @Override
    public long count() {
        return fileCategoryRepository.count();
    }

}
