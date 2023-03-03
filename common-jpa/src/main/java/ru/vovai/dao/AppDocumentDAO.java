package ru.vovai.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vovai.entity.AppDocument;

public interface AppDocumentDAO extends JpaRepository<AppDocument, Long> {
}
