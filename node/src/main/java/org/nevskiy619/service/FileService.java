package org.nevskiy619.service;

import org.nevskiy619.entity.AppDocument;
import org.nevskiy619.entity.AppPhoto;
import org.nevskiy619.service.enums.LinkType;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface FileService {
    AppDocument processDoc(Message telegramMessage);
    AppPhoto processPhoto(Message telegramMessage);
    String generatedLink(Long docId, LinkType linkType);
}
