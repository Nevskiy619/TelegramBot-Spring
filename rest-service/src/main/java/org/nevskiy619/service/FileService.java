package org.nevskiy619.service;

import org.nevskiy619.entity.AppDocument;
import org.nevskiy619.entity.AppPhoto;
import org.nevskiy619.entity.BinaryContent;
import org.springframework.core.io.FileSystemResource;

public interface FileService {
    AppDocument getDocument(String id);
    AppPhoto getPhoto(String id);
}
