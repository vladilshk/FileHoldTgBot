package ru.vovai.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import ru.vovai.entity.AppDocument;
import ru.vovai.entity.AppPhoto;
import ru.vovai.entity.BinaryContent;

public interface FileService {
    AppDocument getDocument(String id);
    AppPhoto getPhoto(String id);
    FileSystemResource getFileSystemResource(BinaryContent binaryContent);
}
