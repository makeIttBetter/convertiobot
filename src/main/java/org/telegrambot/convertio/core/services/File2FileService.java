package org.telegrambot.convertio.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.telegrambot.convertio.core.model.files.File2File;
import org.telegrambot.convertio.core.model.files.FileType;
import org.telegrambot.convertio.core.repositories.File2FileRepository;
import org.telegrambot.convertio.exceptions.services.ObjectNotFoundException;
import org.telegrambot.convertio.exceptions.services.ServiceException;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class File2FileService implements Service<File2File, Long> {
    @Resource
    private final File2FileRepository file2FileRepository;

    @Override
    public File2File create(File2File file2File) {
        return file2FileRepository.save(file2File);
    }

    public List<File2File> createAll(List<File2File> file2Files) {
        return file2FileRepository.saveAll(file2Files);
    }

    @Override
    public File2File update(File2File file2File) {
        return file2FileRepository.save(file2File);
    }

    public List<File2File> updateAll(List<File2File> file2Files) {
        return file2FileRepository.saveAll(file2Files);
    }

    @Override
    public File2File read(Long id) throws ServiceException {
        File2File file2File = file2FileRepository.findById(id).orElse(null);
        if (file2File == null) {
            throw new ObjectNotFoundException("Entity not found");
        }
        return file2File;
    }

    @Override
    public void delete(Long id) {
        file2FileRepository.deleteById(id);
    }

    @Override
    public List<File2File> getAll() {
        return file2FileRepository.findAll();
    }

    @Override
    public long count() {
        return file2FileRepository.count();
    }

    public Set<File2File> findAllByType(FileType type) {
        return file2FileRepository.findAllByType(type);
    }

}

