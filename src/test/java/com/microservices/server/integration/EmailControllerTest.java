package com.microservices.server.integration;

import com.microservices.server.dtos.EmailDto;
import com.microservices.server.models.EmailModel;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;


@SpringBootTest(webEnvironment = DEFINED_PORT)
@Transactional
public class EmailControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

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
    public void shouldReturnErrorWithEmailToEmpty() {
        emailDto.setEmailTo(null);

        EmailModel emailModel = emailDto.getEmailModel();

        HttpEntity<EmailModel> httpEntity = new HttpEntity<>(emailModel);

        ResponseEntity<EmailDto> response = this.testRestTemplate
                .exchange("/sending-email", HttpMethod.POST, httpEntity, EmailDto.class);

        assertThat(response.getStatusCode(), Is.is(HttpStatus.BAD_REQUEST));
    }
}
