package com.example.taskManagmentSystem.AuthService.models;

import lombok.Data;

@Data
public class SignUpModel {
    /**
     * User first name.
     */
    private String firstName;

    /**
     * User last name.
     */
    private String lastName;

    /**
     * User user name.
     */
    private String userName;

    /**
     * User email.
     */
    private String email;

    /**
     * User unhashed password.
     */
    private String password;
}
