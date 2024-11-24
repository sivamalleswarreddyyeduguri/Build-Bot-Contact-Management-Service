package com.buildbot.contactsmanagement.service;

import com.buildbot.contactsmanagement.model.UserDto;

/**
 * This interface defines the service layer for user management.
 * It provides methods for creating and managing users.
 */
public interface UserService {

    /**
     * Saves a new user to the system.
     *
     * @param userDto the user data to be saved
     */
    void saveUser(UserDto userDto);
}