package com.example.taskManagmentSystem.AuthService.models;

import java.util.Date;

import lombok.Data;

/**
 * Token model class.
 */
@Data
public class TokenModel {
    /**
     * unique id of the user.
     */
    private int id;
    /**
     * username of the user.
     */
    private String userName;
    /**
     * first name of the user.
     */
    private String firstName;
    /**
     * last name of the user.
     */
    private String lastName;
    /**
     * email of the user.
     */
    private String email;
    /**
     * issued at date which contains date at which token got generated.
     */
    private Date issuedAt;
    /**
     * expiration date which contains date at which token got expired.
     */
    private Date expiration;
}