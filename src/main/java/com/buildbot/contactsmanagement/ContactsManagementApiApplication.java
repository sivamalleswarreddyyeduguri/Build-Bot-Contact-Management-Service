package com.buildbot.contactsmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Contacts Management", version = "1.0", contact = @Contact(name = "Siva Malleswar Reddy", email = "yedugurisiva121@gmail.com", url = "www.linkedin.com/in/yeduguri-reddy"), description = "We'll build a robust and scalable backend for a Contacts Management Application using Spring Boot. This API will provide RESTful endpoints to handle CRUD operations, contact merging, and user authentication."))
@EnableCaching
@EnableJpaAuditing
public class ContactsManagementApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContactsManagementApiApplication.class, args);
	}

}
