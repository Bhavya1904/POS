package com.pos.service;

import com.pos.exception.UserException;
import com.pos.payload.dto.UserDTO;
import com.pos.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse signup(UserDTO userDto) throws UserException;
    AuthResponse login(UserDTO userDto) throws UserException;

}
