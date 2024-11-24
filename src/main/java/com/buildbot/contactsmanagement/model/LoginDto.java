package com.buildbot.contactsmanagement.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDto {

    @NotBlank(message = "Username is required and cannot be blank")
    private String userName;

    @NotBlank(message = "Password is required and cannot be blank")
    private String password;
}
