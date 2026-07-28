package com.blogApp.repository;

import com.blogApp.entities.Blogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blogs, String> {
}
