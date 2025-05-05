package com.showroommanagement.service;

import com.showroommanagement.dto.SignInDTO;
import com.showroommanagement.dto.SignUpDTO;
import com.showroommanagement.entity.User;
import com.showroommanagement.exception.BadRequestServiceAlertException;
import com.showroommanagement.repository.UserRepository;
import com.showroommanagement.util.Constant;
import com.showroommanagement.util.UserCredentialValidation;
import com.showroommanagement.util.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JWTService jwtService;

    private final UserRepository userRepository;

    private final UserCredentialValidation userCredentialValidation;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public User adminCreate(final SignUpDTO signUpDTO) {
        if (!userCredentialValidation.isValidEmail(signUpDTO.getEmail())) {
            throw new BadRequestServiceAlertException("Invalid Email format");
        }
        if (userRepository.existsByEmail(signUpDTO.getEmail())) {
            throw new BadRequestServiceAlertException(Constant.EXIST_ACCOUNT);
        }
        final User user = new User();
        user.setName(signUpDTO.getName());
        user.setEmail(signUpDTO.getEmail());
        user.setPassword(encoder.encode(signUpDTO.getPassword()));
        user.setUserType(UserType.ADMIN);
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestServiceAlertException(Constant.EXIST_ACCOUNT);
        }
        return this.userRepository.save(user);
    }

    public User employeeCreate(final SignUpDTO signUpDTO) {
        if (!userCredentialValidation.isValidEmail(signUpDTO.getEmail())) {
            throw new BadRequestServiceAlertException("Invalid Email format");
        }
        if (userRepository.existsByEmail(signUpDTO.getEmail())) {
            throw new BadRequestServiceAlertException(Constant.EXIST_ACCOUNT);
        }
        final User user = new User();
        user.setName(signUpDTO.getName());
        user.setEmail(signUpDTO.getEmail());
        user.setPassword(encoder.encode(signUpDTO.getPassword()));
        user.setUserType(UserType.EMPLOYEE);
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestServiceAlertException(Constant.EXIST_ACCOUNT);
        }
        return this.userRepository.save(user);
    }

    public User customerCreate(final SignUpDTO signUpDTO) {
        if (!userCredentialValidation.isValidEmail(signUpDTO.getEmail())) {
            throw new BadRequestServiceAlertException("Invalid Email format");
        }
        if (userRepository.existsByEmail(signUpDTO.getEmail())) {
            throw new BadRequestServiceAlertException(Constant.EXIST_ACCOUNT);
        }
        final User user = new User();
        user.setName(signUpDTO.getName());
        user.setEmail(signUpDTO.getEmail());
        user.setPassword(encoder.encode(signUpDTO.getPassword()));
        user.setUserType(UserType.CUSTOMER);
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestServiceAlertException(Constant.EXIST_ACCOUNT);
        }
        return this.userRepository.save(user);
    }

    @Transactional
    public Map<String, String> signIn(final SignInDTO signInDTO) {
        if (!userCredentialValidation.isValidEmail(signInDTO.getEmail())) {
            throw new BadRequestServiceAlertException("Invalid Email format");
        }
        final User user = this.userRepository.findByEmail(signInDTO.getEmail()).orElseThrow(() -> new RuntimeException(Constant.INCORRECT_EMAIL));
        if (!encoder.matches(signInDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException(Constant.INCORRECT_PASSWORD);
        }
        final String jwt = jwtService.generateToken(user);
        final String refreshToken = jwtService.generateRefreshToken(user);
        final Map<String, String> jwtAuthResp = new HashMap<>();
        jwtAuthResp.put("token", jwt);
        jwtAuthResp.put("refreshToken", refreshToken);
        return jwtAuthResp;
    }

    @Transactional
    public Map<String, String> refreshToken(final String refreshToken) {
        final String userEmail = jwtService.extractUserName(refreshToken);
        final User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException(Constant.INCORRECT_EMAIL));
        if (jwtService.isTokenValid(refreshToken, user)) {
            final var jwt = jwtService.generateToken(user);
            final Map<String, String> response = new HashMap<>();
            response.put("token", jwt);
            response.put("refreshToken", refreshToken);
            return response;
        }
        throw new BadRequestServiceAlertException(Constant.INVALID_TOKEN);
    }

    public User retrieveUserById(final int id) {
        return this.userRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constant.USER_NOT_FOUND));
    }

    public List<User> retrieveUser() {
        return this.userRepository.findAll();
    }

    public String removeById(final int id) {
        final User user = this.userRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST));
        this.userRepository.delete(user);
        return Constant.REMOVE;
    }
}