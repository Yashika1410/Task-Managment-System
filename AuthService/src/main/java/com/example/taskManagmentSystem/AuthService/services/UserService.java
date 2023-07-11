package com.example.taskManagmentSystem.AuthService.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.taskManagmentSystem.AuthService.entities.User;
import com.example.taskManagmentSystem.AuthService.models.SignUpModel;
import com.example.taskManagmentSystem.AuthService.repositories.UserRepo;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * User service class to perform operations for controller class.
 */
@Service
public class UserService {
    /**
     * Hashing cost.
     */
    private static final int HASH_COST = 10;
    /**
     * Autowired User repo interface for db operations.
     */
    @Autowired
    private UserRepo userRepo;

    /**
     * User Service method for creating new user and saving hash password.
     * 
     * @param user
     * @return User object.
     */
    public User registerUser(final SignUpModel user) {
        if (userRepo.existsByEmail(user.getEmail().toLowerCase()) 
        || userRepo.existsByUserName(user.getUserName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "User Already Exits by this email or User name");
        } else {
            user.setPassword(BCrypt.withDefaults().hashToString(HASH_COST,
                    user.getPassword().toCharArray()));
            User newUser = new User();
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setUserName(user.getUserName());
            return userRepo.save(newUser);
        }
        
    }
    
    /**
     * User service method to chack user email and password and return user
     * details if valid.
     * 
     * @param emailOrUserName    user email or password.
     * @param password user unhased password.
     * @return user details if user email and password are valid.
     */
    public final User loginUser(final String emailOrUserName, final String password) {
        if (userRepo.existsByEmail(emailOrUserName)) {
            User userDetails = userRepo.findByEmail(emailOrUserName);
            if (BCrypt.verifyer().verify(password.toCharArray(),
                    userDetails.getPassword()) != null) {
                return userDetails;
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                        "Incorrect Password");
            }
        } else if(userRepo.existsByUserName(emailOrUserName)){
            User userDetails = userRepo.findByUserName(emailOrUserName);
            if (BCrypt.verifyer().verify(password.toCharArray(),
                    userDetails.getPassword()) != null) {
                return userDetails;
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                        "Incorrect Password");
            }
    } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "User Not Found");
        }

    }

    /**
     * @param email
     * @return boolean if user exits or not.
     */
    public final boolean checkUserByEmail(final String email) {
        return userRepo.existsByEmail(email);
    }
    
    /**
     * @param userName
     * @return boolean if user exits or not.
     */
    public final boolean checkUserByUserName(final String userName) {
        return userRepo.existsByUserName(userName);
    }
}
