package com.buildbot.contactsmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buildbot.contactsmanagement.entity.User;

/**
 * This interface extends JpaRepository and provides methods for accessing and manipulating User entities in the database.
 * JpaRepository provides basic CRUD (Create, Read, Update, Delete) operations.
 *
 * @param <User> the entity type this repository manages (User.class)
 * @param <Integer> the ID type of the User entity (usually Integer)
 * 
 * @Repository indicates that this class is a Spring Data JPA repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Finds a user by their email address.
     *
     * @param email the email address of the user to find (note: method name suggests username)
     * @return an Optional containing the User object if found, or Optional.empty() if not found
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to find
     * @return an Optional containing the User object if found, or Optional.empty() if not found
     */
    Optional<User> findByUserName(String username);
}
