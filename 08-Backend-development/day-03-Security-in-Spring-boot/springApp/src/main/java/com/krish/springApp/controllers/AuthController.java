package com.krish.springApp.controllers;

import com.krish.springApp.dto.*;
import com.krish.springApp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/login")
    public ResponseEntity<UserDto> loginUser(@RequestBody LoginDto loginDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.loginUser(loginDto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(userId));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUser() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUser());
    }

    @DeleteMapping("/user/{userId}")
    public String deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return "User deleted successfully";
    }

    @PatchMapping("/user/name/{userId}")
    public ResponseEntity<UserDto> updateName(@PathVariable String userId, @RequestBody UpdateNameDto updateNameDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateName(userId, updateNameDto));
    }

    @PatchMapping("/user/email/{userId}")
    public ResponseEntity<UserDto> updateEmail(@PathVariable String userId, @RequestBody UpdateEmailDto updateEmailDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateEmail(userId, updateEmailDto));
    }

    @PatchMapping("/user/password/{userId}")
    public ResponseEntity<UserDto> updatePassword(@PathVariable String userId, @RequestBody UpdatePasswordDto updatePasswordDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updatePassword(userId, updatePasswordDto));
    }
}
