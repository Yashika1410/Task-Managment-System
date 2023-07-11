package com.example.taskManagmentSystem.AuthService.services;

import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.taskManagmentSystem.AuthService.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.IOException;
import io.jsonwebtoken.security.Keys;

@Service
public class UserJwtTokenService {
    /**
     * jwt key for security key generation.
     */
    private String jwtKey;
    /**
     * jwt issuer details for token generation.
     */
    private String jwtIssuer;

    /**
     * Parameterized constructor to get data from application properties.
     * 
     * @param key
     * @param issuer
     */
    public UserJwtTokenService(
            @Value("${jwt.security.key}") final String key,
            @Value("${jwt.issuer}") final String issuer) {
        this.jwtIssuer = issuer;
        this.jwtKey = key;
    }

    /**
     * JWT Service method to get token.
     * 
     * @param user
     * @return string which contains token.
     */
    public final String getToken(final User user) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, 1);
        Date expiration = calendar.getTime();

        return Jwts.
        builder().setId(uuid).setSubject(String.valueOf(user.getId()))
                .claim("username", user.getUserName())
                .claim("name", (user.getFirstName() + " " + user.getLastName()))
                .claim("email", user.getEmail().toLowerCase())
                .setIssuedAt(date).setExpiration(expiration)
                .setIssuer(jwtIssuer).signWith(
                        Keys.hmacShaKeyFor(jwtKey.getBytes()),
                        SignatureAlgorithm.HS512)
                .compact();

    }

    /**
     * @param token String which contains jwt token.
     * @return Jws<Claims> object which verify if token is expired or not.
     * @throws IOException
     * @throws URISyntaxException
     */
    public final Jws<Claims> verify(final String token) throws IOException,
            URISyntaxException {
        return Jwts.parserBuilder().setSigningKey(
                Keys.hmacShaKeyFor(jwtKey.getBytes()))
                .build().parseClaimsJws(token);
    }
}
