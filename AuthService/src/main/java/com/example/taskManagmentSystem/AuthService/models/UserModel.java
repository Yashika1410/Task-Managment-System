package com.example.taskManagmentSystem.AuthService.models;

import com.example.taskManagmentSystem.AuthService.entities.User;
import com.example.taskManagmentSystem.AuthService.entities.User.UserRole;

import lombok.Data;

@Data
public class UserModel {
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


    public UserModel(User user){
        id = user.getId();
        lastName = user.getLastName();
        email = user.getEmail();
        firstName = user.getFirstName();
        userName = user.getUserName();
        role = user.getRole();
    }
}
