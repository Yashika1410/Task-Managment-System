package com.example.taskManagmentSystem.AuthService.services;

import org.springframework.stereotype.Service;
import at.favre.lib.crypto.bcrypt.BCrypt;

@Service
public class PasswordService {
    /**
     * Hashing cost.
     */
    private static final int HASH_COST = 10;

    public boolean verifyPassword(final String password, final String hashedPassword) {
        return BCrypt.verifyer().verify(password.toCharArray(),
                hashedPassword).verified;
    }

    public String hashPassword(final String password) {
        return BCrypt.withDefaults().hashToString(HASH_COST,
                password.toCharArray());
    }
}
