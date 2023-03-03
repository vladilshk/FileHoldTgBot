package ru.vovai.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vovai.entity.BinaryContent;

public interface BinaryContentDAO extends JpaRepository<BinaryContent, Long> {
}
