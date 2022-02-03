package org.telegrambot.convertio.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.telegrambot.convertio.core.model.files.FileCategory;
import org.telegrambot.convertio.core.model.files.FileType;

import java.util.List;
import java.util.Set;

@Repository
public interface FileTypeRepository extends JpaRepository<FileType, Long> {

    List<FileType> findAllByCodeIn(Set<String> typeCodes);

    FileType findByCode(String code);

    List<FileType> findAllByCategory(FileCategory fileCategory);

}