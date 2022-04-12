package com.microservices.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(ServerApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Starting API e-mail sending");
		SpringApplication.run(ServerApplication.class, args);
		LOGGER.info("API e-mail ready");
	}

}
