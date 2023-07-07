package com.example.taskManagmentSystem.AuthService.models;

import lombok.Data;

/**
 * User Model class for response.
 */
@Data
public class LoginModel {
    /**
     * User email.
     */
    private String email;

    /**
     * User unhashed password.
     */
    private String password;

    /**
     * @param userEmail
     * @param userPassword
     */
    public LoginModel(final String userEmail, final String userPassword) {
        this.email = userEmail;
        this.password = userPassword;
    }
}
