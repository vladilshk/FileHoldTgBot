package ru.vovai.service;

import ru.vovai.utils.dto.MailParams;

public interface MailSenderService {
    void send(MailParams mailParams);
}
