package com.demo.blog.postservice.category;

import com.demo.blog.postservice.category.dto.CategoryRequest;
import com.demo.blog.postservice.category.dto.CategoryResponse;
import com.demo.blog.postservice.category.exception.CategoryAlreadyExistsException;
import com.demo.blog.postservice.category.exception.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    CategoryResponse getByName(String categoryName) {
        if (categoryName == null)
            throw new NullPointerException("Category name was null");
        return new CategoryResponse(categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new CategoryNotFoundException(categoryName)));
    }

    CategoryResponse getById(Long id) {
        return new CategoryResponse(categoryRepository.findById(id).get());
    }

    List<CategoryResponse> getAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryResponse::new)
                .toList();
    }

    CategoryResponse add(CategoryRequest request) {
        Category category = new CategoryBuilder()
                .fromRequest(request)
                .build();
        if (categoryRepository.existsByName(category.getName())) {
            throw new CategoryAlreadyExistsException(category.getName());
        }
        return new CategoryResponse(categoryRepository.save(category));
    }
}
