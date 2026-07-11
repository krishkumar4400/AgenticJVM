package com.dbConnectivity.dbConnectivity.repository;

import com.dbConnectivity.dbConnectivity.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
