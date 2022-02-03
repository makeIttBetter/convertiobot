package org.telegrambot.convertio.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.telegrambot.convertio.core.model.files.File2File;
import org.telegrambot.convertio.core.model.files.FileType;

import java.util.Set;

public interface File2FileRepository extends JpaRepository<File2File, Long> {
    Set<File2File> findAllByType(FileType type);
}