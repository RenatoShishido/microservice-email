package com.microservices.server.services;


import com.microservices.server.dtos.EmailDto;
import com.microservices.server.models.EmailModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class EmailServicesTest {
    
    @Mock
    EmailService emailService;

    @InjectMocks
    JavaMailSenderImpl emailSender;

    @Captor
    ArgumentCaptor<EmailModel> emailCaptor;

    EmailDto emailDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        emailDto = new EmailDto().toBuilder()
                .emailTo("teste@services.com")
                .emailFrom("ms-api@services.com")
                .ownerRef("ms-api")
                .subject("microservices e-mail.")
                .text("this application is a e-mail microservice with use rabbitMQ and others things.")
                .build();
    }
    
    @Test
    public void shouldReturnErrorWithEmailToEmpty() {
        emailDto.setEmailTo(null);
        
        when(emailService.sendEmail(emailDto.getEmailModel())).thenThrow(new Exception("Error unexpected"));
        
        Mockito.verify(emailService , times(1))
                .sendEmail(emailCaptor.capture());

        Assertions.assertEquals(emailDto.getEmailModel(), emailCaptor.getValue(), "Envio de e-mail inválido");

    }
    
    @Test
    public void shouldReturnErrorWithEmailFromEmpty() {
        emailDto = emailDto.toBuilder()
                .emailTo("teste@services.com")
                .emailFrom("ms-api@services.com")
                .ownerRef("ms-api")
                .subject("microservices e-mail.")
                .text("this application is a e-mail microservice with use rabbitMQ and others things.")
                .build();

        ArgumentCaptor<EmailModel> argumentCaptor = ArgumentCaptor.forClass(EmailModel.class);
        
        emailService.sendEmail(emailDto.getEmailModel());

        Mockito.verify(emailService , Mockito.atLeast(1))
                .sendEmail(argumentCaptor.capture());

        Assertions.assertEquals(emailDto.getEmailModel(), argumentCaptor.getValue(), "Envio de e-mail inválido");
    }

    @Test
    public void should_createEmail_when_sendEmail() {
        Mockito.verify(emailService , times(1))
                .sendEmail(emailCaptor.capture());

        Assertions.assertEquals(emailDto.getEmailModel(), emailCaptor.getValue(), "Envio de e-mail inválido");
    }
}
