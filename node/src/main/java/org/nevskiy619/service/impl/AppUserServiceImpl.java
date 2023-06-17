package org.nevskiy619.service.impl;

import lombok.extern.log4j.Log4j;
import org.nevskiy619.dao.AppUserDAO;
import org.nevskiy619.dto.MailParams;
import org.nevskiy619.entity.AppUser;
import org.nevskiy619.service.AppUserService;
import org.nevskiy619.utils.CryptoTool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import static org.nevskiy619.entity.enums.UserState.BASIC_STATE;
import static org.nevskiy619.entity.enums.UserState.WAIT_FOR_EMAIL_STATE;
@Log4j
@Service
public class AppUserServiceImpl implements AppUserService {
    @Value("${service.mail.uri}")
    private String mailServiceUri;
    private final AppUserDAO appUserDAO;
    private final CryptoTool cryptoTool;

    public AppUserServiceImpl(AppUserDAO appUserDAO, CryptoTool cryptoTool) {
        this.appUserDAO = appUserDAO;
        this.cryptoTool = cryptoTool;
    }

    @Override
    public String registerUser(AppUser appUser) {
        if(appUser.getIsActive()){
            return "Вы уже зарегестрированы!";
        } else if(appUser.getEmail() != null){
            return "Вам на почту уже отправлено письмо. Перейдите по ссылке в нём";
        }
        appUser.setState(WAIT_FOR_EMAIL_STATE);
        appUserDAO.save(appUser);
        return "Введите пожалуйста ваш email";
    }

    @Override
    public String setEmail(AppUser appUser, String email) {
        try{
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException e) {
            return "Вы ввели не корректный email. Для отмены команды введите /cancel";
        }
        var optional = appUserDAO.findByEmail(email);
        if(optional.isEmpty()){
            appUser.setEmail(email);
        appUser.setState(BASIC_STATE);
        appUser = appUserDAO.save(appUser);

        var cryptoUserId = cryptoTool.hashOf(appUser.getId());
            var response = sendRequestToMailService(cryptoUserId, email);
            if (response.getStatusCode() != HttpStatus.OK) {
                var msg = String.format("Отправка эл. письма на почту %s не удалась", email);
                log.error(msg);
                appUser.setEmail(null);
                appUserDAO.save(appUser);
                return msg;
            }
            return "Вам на почут отправлено письмо, там ссылка, перейдите по ней";
        } else{
            return "Данная почта используется. Для отмены команды введите /cancel";
        }
    }

    private ResponseEntity<?> sendRequestToMailService(String cryptoUserId, String email) {
        var restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var mailParams = MailParams.builder()
                .id(cryptoUserId)
                .emailTo(email)
                .build();
        var request = new HttpEntity<>(mailParams, headers);
        return restTemplate.exchange(mailServiceUri,
                HttpMethod.POST,
                request,
                String.class);
    }

}
