package com.showroommanagement.service;

import com.showroommanagement.dto.SignInDTO;
import com.showroommanagement.dto.SignUpDTO;
import com.showroommanagement.entity.User;
import com.showroommanagement.exception.BadRequestServiceAlertException;
import com.showroommanagement.repository.UserRepository;
import com.showroommanagement.util.Constant;
import com.showroommanagement.util.UserCredentialValidation;
import com.showroommanagement.util.UserType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private JWTService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserCredentialValidation userCredentialValidation;

    @BeforeAll
    public static void toStartUserDetailService() {
        System.out.println("User Service Test case execution has been Started");
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private SignUpDTO createSignUpDTO(String email) {
        SignUpDTO dto = new SignUpDTO();
        dto.setName("test");
        dto.setEmail(email);
        dto.setPassword("password123");
        return dto;
    }

    private User createUser(UserType type) {
        User user = new User();
        user.setId(1);
        user.setName("test");
        user.setEmail("test@example.com");
        user.setPassword(new BCryptPasswordEncoder().encode("password123"));
        user.setUserType(type);
        return user;
    }

    @Test
    void testAdminCreate() {
        SignUpDTO dto = createSignUpDTO("admin@example.com");

        when(userCredentialValidation.isValidEmail(dto.getEmail())).thenReturn(true);
        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.adminCreate(dto);

        assertEquals(dto.getEmail(), result.getEmail());
        assertEquals(UserType.ADMIN, result.getUserType());
    }

    @Test
    void testEmployeeCreate() {
        SignUpDTO dto = createSignUpDTO("invalid-email");

        when(userCredentialValidation.isValidEmail(dto.getEmail())).thenReturn(false);

        assertThrows(BadRequestServiceAlertException.class, () -> userService.employeeCreate(dto));
    }

    @Test
    void testCustomerCreate() {
        SignUpDTO dto = createSignUpDTO("test@example.com");

        when(userCredentialValidation.isValidEmail(dto.getEmail())).thenReturn(true);
        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        assertThrows(BadRequestServiceAlertException.class, () -> userService.customerCreate(dto));
    }

    @Test
    void testSignIn() {
        SignInDTO signInDTO = new SignInDTO();
        signInDTO.setEmail("test@example.com");
        signInDTO.setPassword("password123");

        User user = createUser(UserType.ADMIN);

        when(userCredentialValidation.isValidEmail(signInDTO.getEmail())).thenReturn(true);
        when(userRepository.findByEmail(signInDTO.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("jwtToken");
        when(jwtService.generateRefreshToken(user)).thenReturn("refreshToken");

        Map<String, String> result = userService.signIn(signInDTO);

        assertEquals("jwtToken", result.get("token"));
        assertEquals("refreshToken", result.get("refreshToken"));
    }

    @Test
    void testRefreshTokenValid() {
        String refreshToken = "refreshToken";
        User user = createUser(UserType.ADMIN);

        when(jwtService.extractUserName(refreshToken)).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.isTokenValid(refreshToken, user)).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("newJwt");

        Map<String, String> result = userService.refreshToken(refreshToken);

        assertEquals("newJwt", result.get("token"));
        assertEquals(refreshToken, result.get("refreshToken"));
    }

    @Test
    void testRetrieveUserById() {
        User user = createUser(UserType.ADMIN);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User result = userService.retrieveUserById(1);

        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    void testRetrieveUserByIdNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(BadRequestServiceAlertException.class, () -> userService.retrieveUserById(1));
    }

    @Test
    void testRetrieveUser() {
        List<User> userList = List.of(createUser(UserType.CUSTOMER));
        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.retrieveUser();

        assertEquals(1, result.size());
    }

    @Test
    void testRemoveById() {
        User user = createUser(UserType.EMPLOYEE);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        String result = userService.removeById(1);

        assertEquals(Constant.REMOVE, result);
        verify(userRepository).delete(user);
    }

    @Test
    void testRemoveByIdNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(BadRequestServiceAlertException.class, () -> userService.removeById(1));
    }

    @AfterAll
    public static void toEndUserDetailService() {
        System.out.println("User Service Test case has been execution finished");
    }
}
