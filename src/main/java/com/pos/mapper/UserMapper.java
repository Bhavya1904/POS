package com.pos.mapper;

import com.pos.model.User;
import com.pos.payload.dto.UserDto;

public class UserMapper {

    public static UserDto toDTO(User savedUser) {
        UserDto userDto = new UserDto();
        userDto.setFullName(savedUser.getFullName());
        userDto.setId(savedUser.getId());
        userDto.setEmail(savedUser.getEmail());
        userDto.setRole(savedUser.getRole());
        userDto.setPhone(savedUser.getPhone());
        userDto.setCreatedAt(savedUser.getCreatedAt());
        userDto.setUpdatedAt(savedUser.getUpdatedAt());
        userDto.setLastLogin(savedUser.getLastLogin());
        return userDto;
    }
}
