package com.buildbot.contactsmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Contacts Management",
        version = "1.0",
        description = "A robust and scalable backend for a Contacts Management Application using Spring Boot. This API provides RESTful endpoints for CRUD operations, contact merging, and user authentication."
    )
)
@EnableCaching
@EnableJpaAuditing
public class ContactsManagementApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContactsManagementApiApplication.class, args);
	}

}
