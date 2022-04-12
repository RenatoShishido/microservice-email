package com.microservices.server.controllers;

import com.microservices.server.dtos.EmailDto;
import com.microservices.server.models.EmailModel;
import com.microservices.server.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
public class EmailController {

    @Autowired
    EmailService emailService;

    @PostMapping("/sending-email")
    public ResponseEntity<EmailModel> sendingEmail(@RequestBody @Valid EmailDto emailDto) {
        EmailModel emailModel = emailService.sendEmail(emailDto.getEmailModel());
        return new ResponseEntity<>(emailModel, HttpStatus.CREATED);
    }

    @GetMapping("/emails")
    public ResponseEntity<Page<EmailModel>> getAllEmails(@PageableDefault(size = 5, sort = "emailId", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(emailService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/emails/{email}")
    public ResponseEntity<Object> getEmail(@PathVariable(value = "email") UUID emailId) {
        Optional<EmailModel> emailModelOptional = emailService.findById(emailId);
        return emailModelOptional.<ResponseEntity<Object>>map(emailModel -> ResponseEntity.status(HttpStatus.OK).body(emailModel)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found"));
    }


}
