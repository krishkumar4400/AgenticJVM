package com.krish.exceptions.repositories;

import com.krish.exceptions.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
