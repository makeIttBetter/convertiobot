package org.telegrambot.convertio.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.telegrambot.convertio.core.model.files.FileCategory;

@Repository
public interface FileCategoryRepository extends JpaRepository<FileCategory, Long> {
}