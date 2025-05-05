package com.showroommanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.showroommanagement.dto.SignInDTO;
import com.showroommanagement.dto.SignUpDTO;
import com.showroommanagement.entity.User;
import com.showroommanagement.service.UserService;
import com.showroommanagement.util.Constant;
import com.showroommanagement.util.UserType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private User user;

    @BeforeAll
    public static void toStartUserController() {
        System.out.println("User Controller Test case execution has been started");
    }

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testCreateAdmin() throws Exception {
        SignUpDTO signUpDTO = new SignUpDTO();
        signUpDTO.setName("dhinesh");
        signUpDTO.setEmail("dhinesh@gmail.com");
        signUpDTO.setPassword("$2a$12$/xG4S8XKWc/j8/.132yyJurKhgmgLUnDFLDiJ6vejtj.gLsLkbD2S");
        User user = new User();
        user.setEmail("dhinesh@gmail.com");
        user.setUserType(UserType.ADMIN);
        when(userService.adminCreate(any(SignUpDTO.class))).thenReturn(user);

        mockMvc.perform(post("/api/v1/user/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value(user.getEmail()))
                .andExpect(jsonPath("$.data.userType").value(user.getUserType().toString()));
        verify(userService).adminCreate(any(SignUpDTO.class));
    }

    @Test
    public void testCreateEmployee() throws Exception {
        SignUpDTO signUpDTO = new SignUpDTO();
        signUpDTO.setName("aarthi");
        signUpDTO.setEmail("aarthi@gmail.com");
        signUpDTO.setPassword("$2a$12$vcwbRA3H9.Ws6aZOjGwYIe9tGHVISfjMVGhdDDrDCG0ZfZCxHyjPK");
        User user = new User();
        user.setEmail("aarthi@gmail.com");
        user.setUserType(UserType.EMPLOYEE);
        when(userService.employeeCreate(any(SignUpDTO.class))).thenReturn(user);

        mockMvc.perform(post("/api/v1/user/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpDTO)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.data.email").value(user.getEmail()))
                .andExpect((ResultMatcher) jsonPath("$.data.userType").value(user.getUserType().toString()));
        verify(userService).employeeCreate(any(SignUpDTO.class));
    }

    @Test
    public void testCreateCustomer() throws Exception {
        SignUpDTO signUpDTO = new SignUpDTO();
        signUpDTO.setName("viha");
        signUpDTO.setEmail("viha@gmail.com");
        signUpDTO.setPassword("$2a$12$h.N7tgnD99sJQGelaisB..3Cks3mgyMqkChIf/9O38Wqu7rr4VZ8a");
        User user = new User();
        user.setEmail("viha@gmail.com");
        user.setUserType(UserType.CUSTOMER);
        when(userService.customerCreate(any(SignUpDTO.class))).thenReturn(user);

        mockMvc.perform(post("/api/v1/user/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpDTO)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.data.email").value(user.getEmail()))
                .andExpect((ResultMatcher) jsonPath("$.data.userType").value(user.getUserType().toString()));
        verify(userService).customerCreate(any(SignUpDTO.class));
    }

    @Test
    public void testSignIn() throws Exception {
        SignInDTO requestDTO = new SignInDTO();
        requestDTO.setEmail("dhinesh@gmail.com");
        requestDTO.setPassword("$2a$12$/xG4S8XKWc/j8/.132yyJurKhgmgLUnDFLDiJ6vejtj.gLsLkbD2S");

        when(userService.signIn(any(SignInDTO.class)))
                .thenReturn(Map.of("message", "LogIn Successfully"));

        mockMvc.perform(post("/api/v1/user/signIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value(Constant.SIGN_IN))
                .andExpect(jsonPath("$.data.message").value("LogIn Successfully"));

        verify(userService).signIn(any(SignInDTO.class));
    }

    @Test
    public void testRefreshToken() throws Exception {
        when(userService.refreshToken(any(String.class)))
                .thenReturn(Map.of("Token", "newToken"));
        mockMvc.perform(post("/api/v1/user/refresh-token")
                        .param("refreshToken", "RefreshToken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.TOKEN))
                .andExpect(jsonPath("$.data.Token").value("newToken"));
        verify(userService).refreshToken(any(String.class));
    }

    @Test
    public void testRetrieveById() throws Exception {
        user = new User();
        user.setId(1);
        user.setName("dhinesh");
        user.setEmail("dhinesh@gmail.com");
        user.setPassword("$2a$12$/xG4S8XKWc/j8/.132yyJurKhgmgLUnDFLDiJ6vejtj.gLsLkbD2S");
        user.setUserType(UserType.ADMIN);
        when(userService.retrieveUserById(1)).thenReturn(user);
        mockMvc.perform(get("/api/v1/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.RETRIEVE))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("dhinesh"));
    }

    @Test
    public void testRetrieveAll() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("dhinesh");
        user.setEmail("dhinesh@gmail.com");
        user.setPassword("$2a$12$/xG4S8XKWc/j8/.132yyJurKhgmgLUnDFLDiJ6vejtj.gLsLkbD2S");
        user.setUserType(UserType.ADMIN);

        when(userService.retrieveUser()).thenReturn(List.of(user));

        mockMvc.perform(get("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.RETRIEVE))
                .andExpect(jsonPath("$.data[0].name").value("dhinesh"));
    }

    @Test
    public void testDeleteById() throws Exception {
        user = new User();
        user.setId(1);
        user.setName("dhinesh");
        user.setEmail("dhinesh@gmail.com");
        user.setPassword("$2a$12$/xG4S8XKWc/j8/.132yyJurKhgmgLUnDFLDiJ6vejtj.gLsLkbD2S");
        user.setUserType(UserType.ADMIN);
        when(userService.removeById(1)).thenReturn(Map.of("message", "Deleted Successfully").toString());
        mockMvc.perform(delete("/api/v1/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constant.DELETE));
    }

    @AfterAll
    public static void endUserControllerTest() {
        System.out.println("User Controller Test execution finished");
    }
}

