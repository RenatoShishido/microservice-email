package com.microservices.server.services;

import com.microservices.server.enums.StatusEmail;
import com.microservices.server.models.EmailModel;
import com.microservices.server.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    JavaMailSender emailSender;

    public EmailModel sendEmail(EmailModel emailModel) {
        EmailModel model;
        emailModel.setSendDateEmail(LocalDateTime.now());
        try {
            SimpleMailMessage message = createSimpleMailMessage(emailModel);
            emailSender.send(message);

            emailModel.setStatusEmail(StatusEmail.SENT);
        }catch (MailException e) {
            emailModel.setStatusEmail(StatusEmail.ERROR);
        } finally {
            model = emailRepository.save(emailModel);
        }
        return model;
    }

    public SimpleMailMessage createSimpleMailMessage(EmailModel emailModel) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailModel.getEmailFrom());
        simpleMailMessage.setTo(emailModel.getEmailTo());
        simpleMailMessage.setSubject(emailModel.getSubject());
        simpleMailMessage.setText(emailModel.getText());
        return simpleMailMessage;
    }

    public Page<EmailModel> findAll(Pageable pageable) {
        return  emailRepository.findAll(pageable);
    }

    public Optional<EmailModel> findById(UUID emailId) {
        return emailRepository.findById(emailId);
    }

}
