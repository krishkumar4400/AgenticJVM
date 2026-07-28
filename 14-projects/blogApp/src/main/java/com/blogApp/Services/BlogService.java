package com.blogApp.Services;

import com.blogApp.repository.BlogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BlogService {
    private final BlogRepository BlogRepository;
}
