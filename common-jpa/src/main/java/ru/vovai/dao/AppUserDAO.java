package ru.vovai.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vovai.entity.AppUser;

public interface AppUserDAO extends JpaRepository<AppUser, Long> {
    AppUser findAppUserByTelegramUserId(Long id);
}
