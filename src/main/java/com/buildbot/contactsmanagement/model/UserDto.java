package com.buildbot.contactsmanagement.model;

import com.buildbot.contactsmanagement.constants.UserConstants;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * This class represents a Data Transfer Object (DTO) for user information.
 * It is used to transfer user data between the client and the server.
 *
 * @Data generates getters, setters, toString, equals, and hashCode methods from Lombok.
 * @AllArgsConstructor generates a constructor with all fields as arguments.
 */
@Data
@AllArgsConstructor
public class UserDto {

    /**
     * The username of the user.
     * @NotBlank ensures that the field is not null or empty (whitespace).
     * @Pattern validates the username against a specific regex pattern.
     */
    @NotBlank(message = "Username is required")
    @Pattern(regexp = UserConstants.USERNAME_REGEX, message = UserConstants.USERNAME_VALIDATION)
    private String userName;

    /**
     * The password of the user.
     * @NotBlank ensures the field is not null or empty (whitespace).
     * @Pattern validates the password according to the specified regex.
     */
    @NotBlank(message = "Password is required")
    @Pattern(regexp = UserConstants.PASSWORD_REGEX, message = UserConstants.PASSWORD_VALIDATION)
    private String password;

    /**
     * The email address of the user.
     * @NotBlank ensures the field is not null or empty (whitespace).
     * @Email ensures the field contains a valid email address.
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    /**
     * The first name of the user.
     * @NotBlank ensures the field is not null or empty (whitespace).
     */
    @NotBlank(message = "First name is required")
    private String firstName;

    /**
     * The last name of the user.
     * @NotBlank ensures the field is not null or empty (whitespace).
     */
    @NotBlank(message = "Last name is required")
    private String lastName;

    /**
     * The roles assigned to the user.
     * @NotBlank ensures the field is not null or empty (whitespace).
     * This can be a comma-separated string of roles.
     */
    @NotBlank(message = "Roles are required")
    private String roles;
}
