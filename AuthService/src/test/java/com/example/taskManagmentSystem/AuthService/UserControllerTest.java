package com.example.taskManagmentSystem.AuthService;

import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.hamcrest.Matchers.is;
import com.example.taskManagmentSystem.AuthService.entities.User;
import com.example.taskManagmentSystem.AuthService.entities.User.UserRole;
import com.example.taskManagmentSystem.AuthService.models.LoginModel;
import com.example.taskManagmentSystem.AuthService.models.SignUpModel;
import com.example.taskManagmentSystem.AuthService.repositories.UserRepo;
import com.example.taskManagmentSystem.AuthService.services.PasswordService;
import com.example.taskManagmentSystem.AuthService.services.UserJwtTokenService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private PasswordService passwordService;

    @MockBean
    private UserRepo userRepo;

    @Autowired
    private UserJwtTokenService userJwtTokenService;
    
    private User createTestUser(){
        User user = new User();
        user.setId("gvbhnjm4e5cfvg");
        user.setFirstName("test");
        user.setLastName("test_last");
        user.setEmail("test@gmail.com");
        user.setUserName("testuser");
        user.setPassword("test1234");
        user.setRole(UserRole.USER);
        return user;
    }

    private String getToken(User user){
        return "Bearer " + userJwtTokenService.getToken(user);
    }

    @Test
    public void registerUser() throws Exception {
        mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE);
        User user = createTestUser();
        user.setId(null);
        SignUpModel signUpModel = new SignUpModel(user.getFirstName(),
        user.getLastName(),user.getUserName(),user.getEmail(),user.getPassword());
        signUpModel.setRole(null);
        when(userRepo.existsByEmail(signUpModel.getEmail())).thenReturn(false);
        when(userRepo.existsByUserName(signUpModel.getUserName())).thenReturn(false);
        when(passwordService.hashPassword(user.getPassword())).thenReturn(signUpModel.getPassword());
        when(userRepo.save(user)).thenReturn(user);
        mockMvc.perform(
            MockMvcRequestBuilders.post(new URI("/authentication/api/v1/sign-up")).contentType(MediaType.APPLICATION_JSON
            ).content(mapper.writeValueAsString(signUpModel)))
            .andExpect(MockMvcResultMatchers.status().isOk()
            ).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andExpect(
                    MockMvcResultMatchers.jsonPath("$.userName", is(signUpModel.getUserName()))).andExpect(
                    MockMvcResultMatchers.jsonPath("$.email", is(signUpModel.getEmail())));
    }

    @Test
    public void loginUser() throws Exception {
        mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE);
        User user = createTestUser();
        LoginModel loginModel = new LoginModel(user.getUserName(), user.getPassword());
        when(userRepo.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepo.existsByUserName(user.getUserName())).thenReturn(true);
        when(passwordService.verifyPassword(user.getPassword(),user.getPassword())).thenReturn(true);
        when(userRepo.findByUserName(user.getUserName())).thenReturn(user);
        mockMvc.perform(
            MockMvcRequestBuilders.post(new URI("/authentication/api/v1/sign-in")).contentType(MediaType.APPLICATION_JSON
            ).content(mapper.writeValueAsString(loginModel)))
            .andExpect(MockMvcResultMatchers.status().isOk()
            ).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andExpect(
                    MockMvcResultMatchers.jsonPath("$.userName", is(user.getUserName()))).andExpect(
                    MockMvcResultMatchers.jsonPath("$.email", is(user.getEmail())));
    }
    @Test
    public void loginUserPasswordNotMatch() throws Exception {
        mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE);
        User user = createTestUser();
        LoginModel loginModel = new LoginModel(user.getUserName(), user.getPassword());
        when(userRepo.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepo.existsByUserName(user.getUserName())).thenReturn(true);
        when(passwordService.verifyPassword(user.getPassword(),user.getPassword())).thenReturn(false);
        when(userRepo.findByUserName(user.getUserName())).thenReturn(user);
        mockMvc.perform(
            MockMvcRequestBuilders.post(new URI("/authentication/api/v1/sign-in")).contentType(MediaType.APPLICATION_JSON
            ).content(mapper.writeValueAsString(loginModel)))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized()
            );
    }

    @Test
    public void userAlreadyExists() throws Exception {
        mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE);
        User user = createTestUser();
        SignUpModel signUpModel = new SignUpModel(user.getFirstName(),
        user.getLastName(),user.getUserName(),user.getEmail(),user.getPassword());
        signUpModel.setRole(null);
        when(userRepo.existsByEmail(signUpModel.getEmail())).thenReturn(true);
        when(userRepo.existsByUserName(signUpModel.getUserName())).thenReturn(false);
        when(passwordService.hashPassword(user.getPassword())).thenReturn(signUpModel.getPassword());
        when(userRepo.save(user)).thenReturn(user);
         mockMvc.perform(
            MockMvcRequestBuilders.post(new URI("/authentication/api/v1/sign-up")).contentType(MediaType.APPLICATION_JSON
            ).content(mapper.writeValueAsString(signUpModel)))
            .andExpect(MockMvcResultMatchers.status().isConflict());
    }
    @Test
    public void getUserById() throws Exception {
        mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE);
        User user = createTestUser();
        System.out.println(user.getId());
         when(userRepo.fetchByUserNameEmail(user.getEmail(),user.getUserName())).thenReturn(Optional.of(user));
         when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
         mockMvc.perform(
            MockMvcRequestBuilders.get(new URI("/authentication/api/v1/users/"+user.getId())).header("Authorization", getToken(user)))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void getUserByInvalidId() throws Exception {
        mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE);
        User user = createTestUser();
        System.out.println(user.getId());
         when(userRepo.fetchByUserNameEmail(user.getEmail(),user.getUserName())).thenReturn(Optional.of(user));
         when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
         mockMvc.perform(
            MockMvcRequestBuilders.get(new URI("/authentication/api/v1/users/12345")).header("Authorization", getToken(user)))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
}
