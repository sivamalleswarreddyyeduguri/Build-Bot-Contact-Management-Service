package com.buildbot.contactsmanagement.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactDto {

    @NotBlank(message = "First name is required and cannot be blank")
    @Pattern(regexp = "[a-zA-Z ]+", message = "First name can only contain letters and spaces")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    private String firstName;

    @Pattern(regexp = "[a-zA-Z ]+", message = "Last name can only contain letters and spaces")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required and cannot be blank")
    @Email(message = "Invalid email format")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Email should match the format: ^[A-Za-z0-9+_.-]+@(.+)$")
    private String email;

    @NotBlank(message = "Phone number is required and cannot be blank")
    @Pattern(regexp = "(^\\+91[6-9]\\d{9}$)|(^[6-9]\\d{9}$)|(^\\+[0-9]{1,2}[0-9]{10,}$)", 
             message = "Phone number must match the format: (+91[6-9]\\d{9}) | ([6-9]\\d{9}) | (+[0-9]{1,2}[0-9]{10,})")
    private String phoneNo;
}
