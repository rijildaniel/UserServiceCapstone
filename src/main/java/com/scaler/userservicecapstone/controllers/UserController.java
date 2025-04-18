package com.scaler.userservicecapstone.controllers;

import com.scaler.userservicecapstone.dtos.LoginRequestDto;
import com.scaler.userservicecapstone.dtos.LoginResponseDto;
import com.scaler.userservicecapstone.dtos.UserSignupRequestDto;
import com.scaler.userservicecapstone.exceptions.UserLoginException;
import com.scaler.userservicecapstone.models.Token;
import com.scaler.userservicecapstone.models.User;
import com.scaler.userservicecapstone.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody UserSignupRequestDto userSignupRequestDto) {
        return ResponseEntity.ok().body(userService.createUser(userSignupRequestDto.getEmail(),
                userSignupRequestDto.getPassword(), userSignupRequestDto.getRoles()));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) throws UserLoginException {
        Token token = userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setToken(token.getValue());
        loginResponseDto.setExpiryDate(token.getExpiryAt());

        return ResponseEntity.ok().body(loginResponseDto);
    }

    @GetMapping("/validate-token/{token}")
    public ResponseEntity<Boolean> validateToken(@PathVariable String token) throws UserLoginException {
        return ResponseEntity.ok().body(userService.validateToken(token));
    }
}
