package com.pos.mapper;

import com.pos.model.Branch;
import com.pos.model.Store;
import com.pos.model.User;
import com.pos.payload.dto.UserDTO;

public class UserMapper {

    public static UserDTO toDTO(User savedUser) {
        UserDTO userDto = new UserDTO();
        userDto.setFullName(savedUser.getFullName());
        userDto.setId(savedUser.getId());
        userDto.setEmail(savedUser.getEmail());
        userDto.setRole(savedUser.getRole());
        userDto.setPhone(savedUser.getPhone());
        userDto.setCreatedAt(savedUser.getCreatedAt());
        userDto.setUpdatedAt(savedUser.getUpdatedAt());
        userDto.setLastLogin(savedUser.getLastLogin());
        userDto.setBranchId(
                savedUser.getBranch() != null ? savedUser.getBranch().getId() : null
        );

        userDto.setStoreId(
                savedUser.getStore() != null ? savedUser.getStore().getId() : null
        );

        return userDto;
    }

    public static User toEntity(UserDTO userDTO) {
        User createdUser = new User();
        createdUser.setEmail(userDTO.getEmail());
        createdUser.setFullName(userDTO.getFullName());
        createdUser.setRole(userDTO.getRole());
        createdUser.setCreatedAt(userDTO.getCreatedAt());
        createdUser.setUpdatedAt(userDTO.getUpdatedAt());
        createdUser.setLastLogin(userDTO.getLastLogin());
        createdUser.setPhone(userDTO.getPhone());
        createdUser.setPassword(userDTO.getPassword());

        return createdUser;
    }
}



