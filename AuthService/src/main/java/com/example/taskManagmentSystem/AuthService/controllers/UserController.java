package com.example.taskManagmentSystem.AuthService.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.taskManagmentSystem.AuthService.entities.User;
import com.example.taskManagmentSystem.AuthService.models.AuthModel;
import com.example.taskManagmentSystem.AuthService.models.LoginModel;
import com.example.taskManagmentSystem.AuthService.models.SignUpModel;
import com.example.taskManagmentSystem.AuthService.models.TokenModel;
import com.example.taskManagmentSystem.AuthService.models.UserModel;
import com.example.taskManagmentSystem.AuthService.services.UserJwtTokenService;
import com.example.taskManagmentSystem.AuthService.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("authentication/api/v1")
public class UserController {
    /**
     * Autowired UserService class.
     */
    @Autowired
    private UserService userService;
    /**
     * Autowired User JWT token Service.
     */
    @Autowired
    private UserJwtTokenService userJwtTokenService;


    /**
     * Method that get called when sign up got hit.
     * 
     * @param user
     * @return returns auth model which contains user email and token.
     */
    @PostMapping("/sign-up")
    public AuthModel signUp(@Valid @RequestBody final SignUpModel user) {
        User newUser = userService.registerUser(user);
        return new AuthModel(newUser,
        userJwtTokenService.getToken(newUser));
    }

    /**
     * Method that get called when sig in got hit.
     * 
     * @param user
     * @return returns auth model which contains user email and token.
     */
    @PostMapping("/sign-in")
    public AuthModel signIn(@Valid @RequestBody final LoginModel user) {
        User authUser = userService.loginUser(
                user.getEmailOrUserName(), user.getPassword());        
        return new AuthModel(authUser,
        userJwtTokenService.getToken(authUser));
    }
    
    /**
     * Method that get called when verify got hit.
     * 
     * @param bearerToken
     * @return TokenModel object which contains email issue and expiry.
     */
    @GetMapping("/verify")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public TokenModel validateToken(
            @Parameter(hidden = true) @RequestHeader(HttpHeaders.AUTHORIZATION) final String bearerToken) {
       try{
        return userJwtTokenService.validateToken(bearerToken);
    } catch(ResponseStatusException re){
        throw new ResponseStatusException(re.getStatus(),re.getMessage());
    }catch(Exception e){
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
    }
    
    /**
     * get user by id
     * @param id unique id.
     * @param bearerToken token.
     * @return User object which contains user details.
     */
    @GetMapping("/users/{id}")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public UserModel getUser(
            @Parameter(hidden = true) @RequestHeader(HttpHeaders.AUTHORIZATION) final String bearerToken,
            @PathVariable("id") final String id) {
       TokenModel tokenModel = userJwtTokenService.validateToken(bearerToken);
       if(!tokenModel.getEmail().isEmpty() && !tokenModel.getId().isEmpty()){
        return new UserModel(userService.getUserById(id));}
       else{
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Unauthorized");
    }
    }
}
