package com.microservices.server.services;

import com.microservices.server.dtos.EmailDto;
import com.microservices.server.models.EmailModel;
import com.microservices.server.repositories.EmailRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EmailServiceTest {

    @InjectMocks
    EmailService emailService;

    @Mock
    EmailRepository emailRepository;

    @Mock
    JavaMailSenderImpl emailSender;
    
    @Captor
    ArgumentCaptor<EmailModel> emailCaptor;

    EmailDto emailDto;

    @BeforeEach
    public void setUp() {
        emailDto = new EmailDto().toBuilder()
                .emailTo("teste@services.com")
                .emailFrom("ms-api@services.com")
                .ownerRef("ms-api")
                .subject("microservices e-mail.")
                .text("this application is a e-mail microservice with use rabbitMQ and others things.")
                .build();
    }

    @Test
    public void should_createEmail_when_sendEmail() {
        EmailModel emailModel = emailDto.getEmailModel();
        when(emailRepository.save(emailModel)).thenReturn(emailModel);

        emailService.sendEmail(emailModel);

        Mockito.verify(emailRepository , times(1))
                .save(emailCaptor.capture());

        Assertions.assertEquals(emailModel, emailCaptor.getValue(), "Envio de e-mail inválido");
    }

    @Test
    public void shouldReturnErrorWithEmailToEmpty() {
       // TODO precisa implementar ainda
    }

    @Test
    public void shouldReturnErrorWithEmailFromEmpty() {
        // TODO precisa implementar ainda
    }

    
}
