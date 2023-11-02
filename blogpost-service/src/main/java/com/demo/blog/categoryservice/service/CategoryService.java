package com.demo.blog.categoryservice.service;

import com.demo.blog.categoryservice.dto.CategoryRequest;
import com.demo.blog.categoryservice.dto.CategoryResponse;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Validated
public interface CategoryService {

    CategoryResponse getById(Long id);

    CategoryResponse getByName(@NotBlank(message = "Category name must be specified") String name);

    Set<CategoryResponse> getAll();

    CategoryResponse add(CategoryRequest request);

    CategoryResponse replace(Long id, CategoryRequest request);

    void delete(Long id);
}
