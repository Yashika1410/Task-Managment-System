package com.example.taskManagmentSystem.AuthService.models;

import com.example.taskManagmentSystem.AuthService.entities.User;

import lombok.Data;

/**
 * Auth model for user signIn and signUp response.
 */
@Data
public class AuthModel {
    /**
     * user username.
     */
    private String userName;
    /**
     * user email.
     */
    private String email;
    /**
     * User token which contains user details.
     */
    private String token;

    /**
     * @param user
     * @param userToken
     */
    public AuthModel(User user,String userToken){
        token=userToken;
        email=user.getEmail().toLowerCase();
        userName=user.getUserName();
    }

}
