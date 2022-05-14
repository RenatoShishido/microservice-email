package com.microservices.server.services;

import com.microservices.server.dtos.EmailDto;
import com.microservices.server.enums.StatusEmail;
import com.microservices.server.models.EmailModel;
import com.microservices.server.repositories.EmailRepository;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest()
@Transactional
public class EmailServiceTest {

    @Autowired
    EmailService emailService;

    @Mock
    EmailRepository emailRepository;
 
    @Mock
    JavaMailSenderImpl emailSender;
    
    EmailDto emailDto;

    @BeforeEach
    public void init() {        
        emailDto = new EmailDto().toBuilder()
                .emailTo("teste@services.com")
                .emailFrom("ms-api@services.com")
                .ownerRef("ms-api")
                .subject("microservices e-mail.")
                .text("this application is a e-mail microservice with use rabbitMQ and others things.")
                .build();
    }

    @Test
    public void shouldSendEmail() {
        
        EmailModel emailModel = emailService.sendEmail(emailDto.getEmailModel());
        
        assertThat(emailModel.getStatusEmail(), Is.is(StatusEmail.SENT));
        assertThat(emailModel.getEmailTo(), Is.is("teste@services.com"));
        assertThat(emailModel.getEmailFrom(), Is.is("ms-api@services.com"));
        assertThat(emailModel.getOwnerRef(), Is.is("ms-api"));
        assertThat(emailModel.getSubject(), Is.is("microservices e-mail."));
        assertThat(emailModel.getText(), Is.is("this application is a e-mail microservice with use rabbitMQ and others things."));
    }
    
    @Test
    public void shouldDontSendEmailWhenToOcorrAnException() throws Exception {
        throw new Exception("Analisar como testar essa funcionalidade");
    }
}
