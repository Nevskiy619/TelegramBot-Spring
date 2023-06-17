package org.nevskiy619.service;

import org.nevskiy619.dto.MailParams;

public interface MailSenderService {
    void send(MailParams mailParams);
}
