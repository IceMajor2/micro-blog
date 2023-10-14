package com.demo.blog.postservice.service;

import com.demo.blog.postservice.domain.Category;
import com.demo.blog.postservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category get(String categoryName) {
        return null;
    }
}
