package org.nevskiy619.controller;

import org.nevskiy619.dto.MailParams;
import org.nevskiy619.service.MailSenderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/mail")
@RestController
public class MailController {
    private final MailSenderService mailSenderService;

    public MailController(MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }
    @RequestMapping("/send")
    public ResponseEntity<?> sendActivationMail(@RequestBody MailParams mailParams){
        mailSenderService.send(mailParams);
        return ResponseEntity.ok().build();
    }
}
