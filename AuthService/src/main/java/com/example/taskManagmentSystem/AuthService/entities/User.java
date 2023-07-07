package com.example.taskManagmentSystem.AuthService.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.ColumnTransformer;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    /**
     * unique id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * user unique email.
     */
    @Column(name = "email", nullable = false, unique = true)
    @ColumnTransformer(write = "LOWER(?)", read = "LOWER(email)")
    private String email;

    /**
     * user unique username.
     */
    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    /**
     * user first name.
     */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * user last name.
     */
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /**
     * user hashed password.
     */
    @Column(name = "password", nullable = false)
    private String password;
}
