package ru.vovai.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vovai.entity.AppPhoto;

public interface AppPhotoDAO extends JpaRepository<AppPhoto, Long> {
}
