package com.microservices.server.repositories;

import com.microservices.server.models.EmailModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmailRepository extends JpaRepository<EmailModel, UUID> {
    
    EmailModel findEmailModelByOwnerRef(String ownerRef);
    
    EmailModel findEmailModelByEmailTo(String emailTo);
    
    EmailModel findEmailModelByEmailFrom(String emailFrom);
    
}
