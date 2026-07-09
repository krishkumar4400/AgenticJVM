package com.restApi.restApi.Repository;

import com.restApi.restApi.dto.CreateUserDto;
import com.restApi.restApi.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {
    List<UserDto> users = new ArrayList<>();

    public UserRepository() {
        users.add(new UserDto(UUID.randomUUID().toString(), "krish", "krishbgp@gmail.com"));
        users.add(new UserDto(UUID.randomUUID().toString(), "ankit", "ankit@gmail.com"));
        users.add(new UserDto(UUID.randomUUID().toString(), "raj", "raj@gmail.com"));
        users.add(new UserDto(UUID.randomUUID().toString(), "ashish", "ashish@gmail.com"));
    }

    public List<UserDto> findAll() {
        return this.users;
    }

    public UserDto findOne(String id) {
        for (UserDto user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }


    public UserDto save(CreateUserDto createUserDto) {
        UserDto user = new UserDto(UUID.randomUUID().toString(), createUserDto.getName(), createUserDto.getEmail());
        users.add(user);
        return user;
    }

    public UserDto updateUser(String id, CreateUserDto updateUserDto) {
        for (UserDto user : users) {
            if (user.getId().equals(id)) {
                user.setName(updateUserDto.getName());
                user.setEmail(updateUserDto.getEmail());
                return user;
            }
        }
        return null;
    }

    public void deleteUser(String id) {
        Map response = new HashMap<>();
        users.removeIf(user -> user.getId().equals(id));
//        response.put("status", 200);
//        response.put("message", "User has been deleted");
//        return response;
    }
}















