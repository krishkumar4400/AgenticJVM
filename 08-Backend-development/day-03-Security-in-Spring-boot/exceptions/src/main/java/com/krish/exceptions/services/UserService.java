package com.krish.exceptions.services;

import com.krish.exceptions.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public  UserService(UserRepository  userRepository ) {
        this.userRepository = userRepository;
    }

}
