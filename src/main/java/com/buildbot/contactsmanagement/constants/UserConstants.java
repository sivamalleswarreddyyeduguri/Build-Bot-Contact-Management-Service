package com.buildbot.contactsmanagement.constants;

public interface UserConstants {

    String USERNAME_VALIDATION = "Username cannot contain spaces in the middle.";
    String PASSWORD_VALIDATION = "Your password must be at least 5 characters long, " +
                                         "contain at least one uppercase letter, one lowercase letter, " +
                                         "one digit, and one special character.";

    String USERNAME_REGEX = "^[\\S]+$"; 
    String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)[a-zA-Z\\d\\W]{5,}$";  

}
