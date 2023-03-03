package ru.vovai.service.impl;

import lombok.extern.log4j.Log4j;
import org.apache.commons.io.FileUtils;
import org.aspectj.util.FileUtil;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import ru.vovai.dao.AppDocumentDAO;
import ru.vovai.dao.AppPhotoDAO;
import ru.vovai.entity.AppDocument;
import ru.vovai.entity.AppPhoto;
import ru.vovai.entity.BinaryContent;
import ru.vovai.service.FileService;

import java.io.File;
import java.io.IOException;

@Service
@Log4j
public class FileServiceImpl implements FileService {
    private final AppDocumentDAO appDocumentDAO;
    private final AppPhotoDAO appPhotoDAO;

    public FileServiceImpl(AppDocumentDAO appDocumentDAO, AppPhotoDAO appPhotoDAO) {
        this.appDocumentDAO = appDocumentDAO;
        this.appPhotoDAO = appPhotoDAO;
    }


    @Override
    public AppDocument getDocument(String docId) {
        //TODO: add hash decoding
        var id = Long.parseLong(docId);
        return appDocumentDAO.findById(id).orElse(null);
    }

    @Override
    public AppPhoto getPhoto(String photoId) {
        //TODO: add hash decoding
        var id = Long.parseLong(photoId);
        return appPhotoDAO.findById(id).orElse(null);
    }

    @Override
    public FileSystemResource getFileSystemResource(BinaryContent binaryContent) {
        try {
            //TODO: add temp files name generation
            File temp = File.createTempFile("tempFile", ".bin");
            temp.deleteOnExit();;
            FileUtils.writeByteArrayToFile(temp, binaryContent.getFileAsArrayOfBytes());
            return new FileSystemResource(temp);
        } catch (IOException e){
            log.error(e);
            return null;
        }
    }
}
