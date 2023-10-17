package com.demo.blog.postservice.category;

import com.demo.blog.postservice.category.dto.CategoryRequest;
import com.demo.blog.postservice.category.exception.CategoryAlreadyExistsException;
import com.demo.blog.postservice.category.exception.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    Category get(String categoryName) {
        if(categoryName == null)
            throw new NullPointerException("Category name was null");
        return categoryRepository.findByName(categoryName)
                .orElseThrow(CategoryNotFoundException::new);
    }

    List<Category> getAll() {
        return categoryRepository.findAll();
    }

    Category add(CategoryRequest request) {
        Category category = new CategoryBuilder()
                .fromRequest(request)
                .build();
        if(categoryRepository.existsByName(category.getName())) {
            throw new CategoryAlreadyExistsException();
        }
        return categoryRepository.save(category);
    }
}
