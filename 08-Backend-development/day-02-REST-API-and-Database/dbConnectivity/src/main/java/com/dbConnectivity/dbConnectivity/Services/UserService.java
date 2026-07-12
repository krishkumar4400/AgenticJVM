package com.dbConnectivity.dbConnectivity.Services;

import com.dbConnectivity.dbConnectivity.DTO.CreateUserDto;
import com.dbConnectivity.dbConnectivity.DTO.UserDto;
import com.dbConnectivity.dbConnectivity.entities.User;
import com.dbConnectivity.dbConnectivity.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<UserDto> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : users) {
            userDtoList.add(new UserDto(user.getId(), user.getName(), user.getEmail()));
        }
        return userDtoList;
    }

    public UserDto getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        UserDto userDto = new UserDto(user.getId(), user.getName(), user.getEmail());
        return userDto;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public UserDto updateUser(Long id, CreateUserDto updateUserDto) {
        User user = userRepository.findById(id).orElseThrow();
        user.setName(updateUserDto.getName());
        user.setEmail(updateUserDto.getEmail());
//        no save()
//        User savedUser = userRepository.save(user);
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    @Transactional
    public UserDto patchUser(Long id, CreateUserDto patchUserDto) {
        User user = userRepository.findById(id).orElseThrow();

        if (patchUserDto.getName() != null) {
            user.setName(patchUserDto.getName());
        }
        if (patchUserDto.getEmail() != null) {
            user.setEmail(patchUserDto.getEmail());
        }
        // no save()
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }
}
