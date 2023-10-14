package com.demo.blog.postservice.controller;

import com.demo.blog.postservice.domain.Category;
import com.demo.blog.postservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public Category getCategory(@RequestParam("name") String categoryName) {
        return categoryService.get(categoryName);
    }
}
