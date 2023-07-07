package com.example.taskManagmentSystem.AuthService.repositories;

import org.springframework.data.repository.CrudRepository;
import com.example.taskManagmentSystem.AuthService.entities.User;

public interface UserRepo extends CrudRepository<User, Integer> {
    /**
     * checks if user by email already exists or not.
     * 
     * @param email
     * @return boolean if user by exists or not.
     */
    boolean existsByEmail(String email);

    /**
     * Get user details by email.
     * 
     * @param email
     * @return return user object by email.
     */
    User findByEmail(String email);
    
    /**
     * checks if user by user name already exists or not.
     * 
     * @param userName
     * @return boolean if user by exists or not.
     */
    boolean existsByUserName(String userName);

    /**
     * Get user details by user name.
     * 
     * @param userName
     * @return return user object by user name.
     */
    User findByUserName(String userName);
}
