package ru.vovai.service;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vovai.entity.AppDocument;
import ru.vovai.entity.AppPhoto;

public interface FileService {
    AppDocument processDoc(Message telegramMessage);
    AppPhoto processPhoto(Message telegramMessage);

}
