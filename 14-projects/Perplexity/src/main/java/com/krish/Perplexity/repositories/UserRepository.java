package com.krish.Perplexity.repositories;

import com.krish.Perplexity.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
}
