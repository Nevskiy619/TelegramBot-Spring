package org.nevskiy619.service.impl;

import lombok.extern.log4j.Log4j;
import org.nevskiy619.utils.CryptoTool;
import org.nevskiy619.dao.AppDocumentDAO;
import org.nevskiy619.dao.AppPhotoDAO;
import org.nevskiy619.entity.AppDocument;
import org.nevskiy619.entity.AppPhoto;
import org.nevskiy619.service.FileService;
import org.springframework.stereotype.Service;

@Log4j
@Service
public class FileServiceImpl implements FileService {
    private final AppDocumentDAO appDocumentDAO;
    private final AppPhotoDAO appPhotoDAO;
    private final CryptoTool cryptoTool;

    public FileServiceImpl(AppDocumentDAO appDocumentDAO, AppPhotoDAO appPhotoDAO, CryptoTool cryptoTool) {
        this.appDocumentDAO = appDocumentDAO;
        this.appPhotoDAO = appPhotoDAO;
        this.cryptoTool = cryptoTool;
    }

    @Override
    public AppDocument getDocument(String hash) {
        var id = cryptoTool.idOf(hash);
        if(id == null){
            return null;
        }
        return appDocumentDAO.findById(id).orElse(null);
    }

    @Override
    public AppPhoto getPhoto(String hash) {
        var id = cryptoTool.idOf(hash);
        if(id == null){
            return null;
        }
        return appPhotoDAO.findById(id).orElse(null);
    }
}
