package com.buildbot.contactsmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buildbot.contactsmanagement.entity.Contact;

/**
 * This interface extends JpaRepository and provides methods for accessing and manipulating Contact entities in the database.
 * JpaRepository provides basic CRUD (Create, Read, Update, Delete) operations.
 *
 * @param <Contact> the entity type this repository manages (Contact.class)
 * @param <Long> the ID type of the Contact entity (usually Long)
 */
public interface ContactRepository extends JpaRepository<Contact, Long> {

    /**
     * Finds a contact by their email address.
     *
     * @param email the email address of the contact to find
     * @return an Optional containing the Contact object if found, or Optional.empty() if not found
     */
    Optional<Contact> findByEmail(String email);

    /**
     * Finds a contact by their phone number.
     *
     * @param phoneNo the phone number of the contact to find
     * @return an Optional containing the Contact object if found, or Optional.empty() if not found
     */
    Optional<Contact> findByPhoneNo(String phoneNo);
}