package com.microservices.server.repositories;

import com.microservices.server.models.EmailModel;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest()
@Transactional
public class EmailRepositoriesTest {
    
    @Autowired
    EmailRepository emailRepository;
    EmailModel emailModel;
    
    @BeforeEach
    public void init() {
        emailModel = new EmailModel().toBuilder()
                .emailTo("teste@services.com")
                .emailFrom("ms-api@services.com")
                .ownerRef("ms-api")
                .subject("microservices e-mail.")
                .text("this application is a e-mail microservice with use rabbitMQ and others things.")
                .build();

        emailRepository.save(emailModel);
    }

    @Test
    public void searchEmailByAll() {
        List<EmailModel> all = emailRepository.findAll();
        assertThat(all.size(), Is.is(1));
        
        EmailModel email = all.get(0);
        assertThat(email.getEmailTo(), is("teste@services.com"));
        assertThat(email.getEmailFrom(), is("ms-api@services.com"));
        assertThat(email.getOwnerRef(), is("ms-api"));
        assertThat(email.getSubject(), is("microservices e-mail."));
        assertThat(email.getText(), is("this application is a e-mail microservice with use rabbitMQ and others things."));
        assertThat(email.getStatusEmail(), nullValue());
    }

    @Test
    public void searchEmailByOwnerRef() {
        emailRepository.save(new EmailModel().toBuilder()
                .emailTo("teste@services.com")
                .emailFrom("ms-api@services.com")
                .ownerRef("teste-api")
                .subject("microservices e-mail.")
                .text("this application is a e-mail microservice with use rabbitMQ and others things.")
                .build());
        
        EmailModel email = emailRepository.findEmailModelByOwnerRef("teste-api");

        assertThat(emailRepository.findAll().size(), Is.is(2));
        assertThat(email, notNullValue());
        assertThat(email.getEmailTo(), is("teste@services.com"));
        assertThat(email.getEmailFrom(), is("ms-api@services.com"));
        assertThat(email.getOwnerRef(), is("teste-api"));
        assertThat(email.getSubject(), is("microservices e-mail."));
        assertThat(email.getText(), is("this application is a e-mail microservice with use rabbitMQ and others things."));
        assertThat(email.getStatusEmail(), nullValue());
    }

    @Test
    public void searchEmailByEmailTo() {
        emailRepository.save(new EmailModel().toBuilder()
                .emailTo("teste-ms@services.com")
                .emailFrom("ms-api@services.com")
                .ownerRef("ms-api")
                .subject("microservices e-mail.")
                .text("this application is a e-mail microservice with use rabbitMQ and others things.")
                .build());
        
        EmailModel email = emailRepository.findEmailModelByEmailTo("teste-ms@services.com");

        assertThat(emailRepository.findAll().size(), Is.is(2));
        assertThat(email, notNullValue());
        assertThat(email.getEmailTo(), is("teste-ms@services.com"));
        assertThat(email.getEmailFrom(), is("ms-api@services.com"));
        assertThat(email.getOwnerRef(), is("ms-api"));
        assertThat(email.getSubject(), is("microservices e-mail."));
        assertThat(email.getText(), is("this application is a e-mail microservice with use rabbitMQ and others things."));
        assertThat(email.getStatusEmail(), nullValue());
    }
    
    @Test
    public void searchEmailByEmailFrom() {
        emailRepository.save(new EmailModel().toBuilder()
                .emailTo("teste@services.com")
                .emailFrom("teste-api@services.com")
                .ownerRef("ms-api")
                .subject("microservices e-mail.")
                .text("this application is a e-mail microservice with use rabbitMQ and others things.")
                .build());
        
        EmailModel email = emailRepository.findEmailModelByEmailFrom("teste-api@services.com");

        assertThat(emailRepository.findAll().size(), Is.is(2));
        assertThat(email, notNullValue());
        assertThat(email.getEmailTo(), is("teste@services.com"));
        assertThat(email.getEmailFrom(), is("teste-api@services.com"));
        assertThat(email.getOwnerRef(), is("ms-api"));
        assertThat(email.getSubject(), is("microservices e-mail."));
        assertThat(email.getText(), is("this application is a e-mail microservice with use rabbitMQ and others things."));
        assertThat(email.getStatusEmail(), nullValue());
    }
}
