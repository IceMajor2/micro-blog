package com.demo.blog.postservice.category;

import com.demo.blog.postservice.category.Category;
import com.demo.blog.postservice.category.exception.CategoryNotFoundException;
import com.demo.blog.postservice.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category get(String categoryName) {
        if(categoryName == null)
            throw new NullPointerException("Category name was null");
        return categoryRepository.findByName(categoryName)
                .orElseThrow(CategoryNotFoundException::new);
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}
