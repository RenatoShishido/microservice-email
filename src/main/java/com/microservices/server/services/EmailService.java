package com.microservices.server.services;

import com.microservices.server.enums.StatusEmail;
import com.microservices.server.models.EmailModel;
import com.microservices.server.repositories.EmailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    
    @Autowired
    EmailRepository emailRepository;

    @Autowired
    JavaMailSender emailSender;

    public EmailModel sendEmail(EmailModel emailModel) {
        emailModel.setSendDateEmail(LocalDateTime.now());
        
        try {
            SimpleMailMessage message = createSimpleMailMessage(emailModel);
            emailSender.send(message);

            emailModel.setStatusEmail(StatusEmail.SENT);
        }catch (MailException e) {
            emailModel.setStatusEmail(StatusEmail.ERROR);
            LOGGER.info("Sending email error " + e.getMessage());
            throw new Exception("Error unexpected");
        } finally {
            return emailRepository.save(emailModel);
        }
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
