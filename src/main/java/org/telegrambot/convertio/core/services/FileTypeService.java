package org.telegrambot.convertio.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.telegrambot.convertio.core.model.files.FileCategory;
import org.telegrambot.convertio.core.model.files.FileType;
import org.telegrambot.convertio.core.repositories.FileTypeRepository;
import org.telegrambot.convertio.exceptions.services.ObjectNotFoundException;
import org.telegrambot.convertio.exceptions.services.ServiceException;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FileTypeService implements Service<FileType, Long> {
    @Resource
    private final FileTypeRepository fileTypeRepository;

    @Override
    public FileType create(FileType fileType) {
        return fileTypeRepository.save(fileType);
    }

    @Override
    public FileType update(FileType fileType) {
        return fileTypeRepository.save(fileType);
    }

    @Override
    public FileType read(Long id) throws ServiceException {
        FileType fileType = fileTypeRepository.findById(id).orElse(null);
        if (fileType == null) {
            throw new ObjectNotFoundException("Entity not found");
        }
        return fileType;
    }

    @Override
    public void delete(Long id) {
        fileTypeRepository.deleteById(id);
    }

    @Override
    public List<FileType> getAll() {
        return fileTypeRepository.findAll();
    }

    @Override
    public long count() {
        return fileTypeRepository.count();
    }

    public List<FileType> findAllByCodeSet(Set<String> typeCodes) {
        return fileTypeRepository.findAllByCodeIn(typeCodes);
    }

    public Set<FileType> findAllByCategory(FileCategory fileCategory) {
        return new HashSet<>(fileTypeRepository.findAllByCategory(fileCategory));
    }

    public FileType readByCode(String code) throws ServiceException {
        FileType fileType = fileTypeRepository.findByCode(code);
        if (fileType == null) {
            throw new ObjectNotFoundException("Entity not found");
        }
        return fileType;
    }
}

