package com.microservices.server.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class BeanJavaMailSource {
    
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private int port;
    @Value("${spring.mail.username}")
    private String user;
    @Value("${spring.mail.password}")
    private String pass;
    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String starttls;
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String auth;
    @Value("${spring.mail.protocol}")
    private String protocol;
    

    @Bean()
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setProtocol(protocol);
        mailSender.setUsername(user);
        mailSender.setPassword(pass);
        mailSender.setJavaMailProperties(mailProps());
        return mailSender;
    }

    private Properties mailProps() {
        Properties properties = new Properties();
        properties.setProperty("spring.mail.smtp.starttls.enable", starttls);
        properties.setProperty("spring.mail.properties.mail.smtp.auth", auth);
        return properties;
    }
}