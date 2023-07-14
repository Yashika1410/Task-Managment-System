package com.example.taskManagmentSystem.AuthService.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.util.UUID;
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
    private String id;

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

    public enum UserRole {
        USER,
        ADMIN
    }

    /**
     *  role of the user.
     */
    @Column(name = "role", nullable = false)
    private UserRole role;
    
    /**
     * user hashed password.
     */
    @Column(name = "password", nullable = false)
    private String password;

    @PrePersist
    public void setUUID(){
        id = UUID.randomUUID().toString().replace("-", "");
        if(role==null){
            role = UserRole.USER;
        }
    } 
}
