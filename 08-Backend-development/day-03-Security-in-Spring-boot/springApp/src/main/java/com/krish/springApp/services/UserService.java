package com.krish.springApp.services;

import com.krish.springApp.dto.CreateUserDto;
import com.krish.springApp.dto.UserDto;
import com.krish.springApp.entity.User;
import com.krish.springApp.repositories.UserRepository;
import org.springframework.stereotype.Service;

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

        return new UserDto(newUser.getId(), newUser.getName(), newUser.getEmail(), newUser.getEmail(), newUser.getVerified());
    }

    public UserDto getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getEmail(), user.getVerified());
    }



}
