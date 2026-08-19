package com.krish.springApp.services;

import com.krish.springApp.dto.*;
import com.krish.springApp.entity.User;
import com.krish.springApp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto registerUser(CreateUserDto createUserDto) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName(createUserDto.getName());
        user.setEmail(createUserDto.getEmail());
        user.setPassword(createUserDto.getPassword());
        user.setVerified(createUserDto.getIsVerified());

        User newUser = userRepository.save(user);

        return new UserDto(newUser.getId(), newUser.getName(), newUser.getEmail(), newUser.getPassword(), newUser.getVerified());
    }

    public UserDto getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getEmail(), user.getVerified());
    }


    public List<UserDto> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(new UserDto(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getVerified()));
        }
        return userDtos;
    }

    public UserDto loginUser(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail());

        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getEmail(), user.getVerified());
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public UserDto updateName(String userId, UpdateNameDto updateNameDto) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setName(updateNameDto.getName());
        userRepository.save(user);
        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getVerified());
    }
    public UserDto updateEmail(String userId, UpdateEmailDto updateEmailDto) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setEmail(updateEmailDto.getEmail());
        userRepository.save(user);
        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getVerified());
    }

    public UserDto updatePassword(String userId, UpdatePasswordDto updatePasswordDto) {
        User user = userRepository.findById(userId).orElseThrow();

        user.setPassword(updatePasswordDto.getPassword());
        userRepository.save(user);

        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getVerified());
    }
}
