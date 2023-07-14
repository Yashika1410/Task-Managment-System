package com.example.taskManagmentSystem.AuthService.services;

import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.taskManagmentSystem.AuthService.entities.User;
import com.example.taskManagmentSystem.AuthService.models.TokenModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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

    @Autowired
    private UserService userService;

    /**
     * variable which contains starting count of bearer token.
     */
    private static final int SUBSTRING_STARTING = 7;

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
    private final Jws<Claims> verify(final String token) throws IOException,
            URISyntaxException {
        return Jwts.parserBuilder().setSigningKey(
                Keys.hmacShaKeyFor(jwtKey.getBytes()))
                .build().parseClaimsJws(token);
    }

    public final TokenModel validateToken(String bearerToken){
        if (!bearerToken.startsWith("Bearer")
                || bearerToken.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Token type is not bearer");
        }
        String token = bearerToken.substring(SUBSTRING_STARTING,
                bearerToken.length()).strip();
        try {
            Jws<Claims> userJws = verify(token);
            Claims userClaim = userJws.getBody();
            User user = userService.getByEmailAndUserName(
                    userClaim.get("username").toString(),
                    userClaim.get("email").toString().toLowerCase());
            if (!user.getId().equals(userClaim.getSubject().toString())) {
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Unauthorized");
            }
            TokenModel tokenModel = new TokenModel();
            tokenModel.setId(userClaim.getSubject().toString());
            tokenModel.setEmail(user.getEmail());
            tokenModel.setName(user.getFirstName() + " " + user.getLastName());
            tokenModel.setUserName(user.getUserName());
            tokenModel.setRole(user.getRole());
            tokenModel.setIssuedAt(userClaim.getIssuedAt());
            tokenModel.setExpiration(userClaim.getExpiration());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MINUTE, 1);
            if (tokenModel.getExpiration().getTime() < calendar.getTime().getTime()) {
                throw new ExpiredJwtException(userJws.getHeader(),
                        userClaim, "token got expired");
            }
            return tokenModel;
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    e.getMessage());
        } catch (ExpiredJwtException eJwt) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    eJwt.getLocalizedMessage());
        } catch (ResponseStatusException re) {
            throw new ResponseStatusException(re.getStatus(), re.getMessage());
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ex.getMessage());
        }
    }
}
