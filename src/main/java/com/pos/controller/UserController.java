package com.pos.controller;

import com.pos.exception.UserException;
import com.pos.mapper.UserMapper;
import com.pos.model.User;
import com.pos.payload.dto.UserDto;
import com.pos.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(
            @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.getUserFromJwtToken(jwt);

        return ResponseEntity.ok(UserMapper.toDTO(user));
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id) {

        User user = userService.getUserById(id);

        return ResponseEntity.ok(UserMapper.toDTO(user));
    }
}
