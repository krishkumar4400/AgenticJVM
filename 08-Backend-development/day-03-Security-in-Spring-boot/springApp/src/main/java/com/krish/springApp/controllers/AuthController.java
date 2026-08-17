package com.krish.springApp.controllers;

import com.krish.springApp.dto.CreateUserDto;
import com.krish.springApp.dto.UserDto;
import com.krish.springApp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody CreateUserDto createUserDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(createUserDto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(userId));
    }
}
