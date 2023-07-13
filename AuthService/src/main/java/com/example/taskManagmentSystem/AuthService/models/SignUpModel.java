package com.example.taskManagmentSystem.AuthService.models;

import org.springframework.lang.NonNull;

import lombok.Data;

@Data
public class SignUpModel {
    /**
     * User first name.
     */
    @NonNull
    private String firstName;

    /**
     * User last name.
     */
    @NonNull
    private String lastName;

    /**
     * User user name.
     */
    @NonNull
    private String userName;

    /**
     * User email.
     */
    @NonNull
    private String email;

    /**
     * User unhashed password.
     */
    @NonNull
    private String password;
}
