package com.dbConnectivity.dbConnectivity.Services;

import com.dbConnectivity.dbConnectivity.DTO.CreateUserDto;
import com.dbConnectivity.dbConnectivity.DTO.UserDto;
import com.dbConnectivity.dbConnectivity.entities.User;
import com.dbConnectivity.dbConnectivity.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto createUser(CreateUserDto createUserDto) {
        User user = new User();
        user.setName(createUserDto.getName());
        user.setEmail(createUserDto.getEmail());
        User savedUser = userRepository.save(user);
        return new UserDto(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
    }
}
