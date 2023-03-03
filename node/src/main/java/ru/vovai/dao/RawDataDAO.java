package ru.vovai.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vovai.entity.RawData;

public interface RawDataDAO extends JpaRepository<RawData, Long> {

}
