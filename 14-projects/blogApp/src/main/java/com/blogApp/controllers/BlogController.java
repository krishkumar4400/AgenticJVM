package com.blogApp.controllers;

import com.blogApp.Services.BlogService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/blogs")
@AllArgsConstructor
public class BlogController {
    private final BlogService blogService;

}
