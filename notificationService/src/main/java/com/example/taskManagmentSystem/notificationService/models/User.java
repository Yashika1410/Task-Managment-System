package com.example.taskManagmentSystem.notificationService.models;

import lombok.Data;

@Data
public class User {
    public enum UserRole {
        USER,
        ADMIN
    }

    /**
     * unique id.
     */
    private String id;

    /**
     * user unique email.
     */
    private String email;

    /**
     * user unique username.
     */
    private String userName;

    /**
     * user first name.
     */
    private String firstName;

    /**
     * user last name.
     */
    private String lastName;

    /**
     * role of the user.
     */
    private UserRole role;
}
