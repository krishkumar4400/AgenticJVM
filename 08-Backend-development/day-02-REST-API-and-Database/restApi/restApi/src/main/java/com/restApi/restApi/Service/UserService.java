package com.restApi.restApi.Service;

import com.restApi.restApi.Repository.UserRepository;
import com.restApi.restApi.dto.CreateUserDto;
import com.restApi.restApi.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAllUsers() {
        return this.userRepository.findAll();
    }

    public UserDto getUserById(String id) {
        return userRepository.findOne(id);
    }

    public UserDto createUser(CreateUserDto createUserDto) {
        return this.userRepository.save(createUserDto);
    }

    public UserDto updateUser(String id, CreateUserDto updateUserDto) {
        if (userRepository.findOne(id) == null) {
            return null;
        }
        return this.userRepository.updateUser(id, updateUserDto);
    }

    public void deleteUser(String id) {
        this.userRepository.deleteUser(id);
    }
}
