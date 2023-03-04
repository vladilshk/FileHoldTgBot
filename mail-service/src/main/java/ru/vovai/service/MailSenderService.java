package ru.vovai.service;

import ru.vovai.dto.MailParams;

public interface MailSenderService {
    void send(MailParams mailParams);
}
