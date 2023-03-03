package ru.vovai.service;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vovai.entity.AppDocument;

public interface FileService {
    AppDocument processDoc(Message externalMessage);
}
