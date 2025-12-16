package com.pos.controller;

import com.pos.exception.UserException;
import com.pos.payload.dto.UserDTO;
import com.pos.payload.response.AuthResponse;
import com.pos.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

//    localhost:8080/auth/signup
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signupHandler(
            @RequestBody UserDTO userDto) throws UserException {
        return ResponseEntity.ok(
                authService.signup(userDto));
    }
//    localhost:8080/auth/login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginHandler(
            @RequestBody UserDTO userDto) throws UserException {
        return ResponseEntity.ok(
                authService.login(userDto));
    }

}


