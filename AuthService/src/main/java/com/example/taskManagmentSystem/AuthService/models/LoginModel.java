package com.example.taskManagmentSystem.AuthService.models;

import lombok.Data;
import lombok.NonNull;

/**
 * User Model class for response.
 */
@Data
public class LoginModel {
    /**
     * User email.
     */
    @NonNull
    private String emailOrUserName;

    /**
     * User unhashed password.
     */
    @NonNull
    private String password;

}
