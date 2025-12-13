package com.pos.controller;

import com.pos.exception.UserException;
import com.pos.model.User;
import com.pos.payload.dto.StoreDto;
import com.pos.service.StoreService;
import com.pos.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store")
public class StoreController {

    private final StoreService storeService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<StoreDto> createStore(
            @RequestBody StoreDto storeDto,
            @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.getUserFromJwtToken(jwt);

        return ResponseEntity.ok(storeService.createStore(storeDto, user));
    }
    @GetMapping("{/id}")
    public ResponseEntity<StoreDto> getStoreById(@PathVariable Long id,
            @RequestBody StoreDto storeDto,
            @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.getUserFromJwtToken(jwt);

        return ResponseEntity.ok(storeService.createStore(storeDto, user));
    }



}
