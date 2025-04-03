package com.showroommanagement.controller;

import com.showroommanagement.dto.ResponseDTO;
import com.showroommanagement.dto.SignInDTO;
import com.showroommanagement.dto.SignUpDTO;
import com.showroommanagement.service.UserService;
import com.showroommanagement.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/user/admin")
    public ResponseDTO adminCreate(@RequestBody final SignUpDTO signUpDTO) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.CREATE, this.userService.adminCreate(signUpDTO));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
    @PostMapping("/user/employee")
    public ResponseDTO employeeCreate(@RequestBody final SignUpDTO signUpDTO) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.CREATE, this.userService.employeeCreate(signUpDTO));
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    @PostMapping("/user/customer")
    public ResponseDTO customerCreate(@RequestBody final SignUpDTO signUpDTO) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.CREATE, this.userService.customerCreate(signUpDTO));
    }

    @PostMapping("/user/signIn")
    public ResponseDTO signIn(@RequestBody final SignInDTO signInDTO) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.SIGN_IN, this.userService.signIn(signInDTO));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/user/{id}")
    public ResponseDTO retrieveUserById(@PathVariable final int id) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.userService.retrieveUserById(id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/user")
    public ResponseDTO retrieveUser() {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.userService.retrieveUser());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE','CUSTOMER')")
    @DeleteMapping("/user/{id}")
    public ResponseDTO deleteByAdminId(@PathVariable final int id) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.DELETE, this.userService.deleteById(id));
    }

    @PostMapping("user/refresh-token")
    public ResponseDTO refreshToken(final String refreshToken) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.TOKEN, this.userService.refreshToken(refreshToken));
    }

}