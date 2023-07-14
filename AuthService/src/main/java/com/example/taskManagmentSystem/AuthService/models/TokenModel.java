package com.example.taskManagmentSystem.AuthService.models;

import java.util.Date;

import com.example.taskManagmentSystem.AuthService.entities.User.UserRole;

import lombok.Data;

/**
 * Token model class.
 */
@Data
public class TokenModel {
    /**
     * unique id of the user.
     */
    private String id;
    /**
     * username of the user.
     */
    private String userName;
    /**
     * name of the user.
     */
    private String name;
    /**
     * email of the user.
     */
    private String email;
    /**
     * Role of the user.
     */
    private UserRole role;
    /**
     * issued at date which contains date at which token got generated.
     */
    private Date issuedAt;
    /**
     * expiration date which contains date at which token got expired.
     */
    private Date expiration;
}