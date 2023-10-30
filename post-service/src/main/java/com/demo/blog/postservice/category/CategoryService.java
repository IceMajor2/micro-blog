package com.demo.blog.postservice.category;

import com.demo.blog.postservice.category.dto.CategoryRequest;
import com.demo.blog.postservice.category.dto.CategoryResponse;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Validated
interface CategoryService {

    CategoryResponse getById(Long id);

    CategoryResponse getByName(@NotBlank(message = "Category name must be specified") String name);

    Set<CategoryResponse> getAll();

    CategoryResponse add(CategoryRequest request);

    CategoryResponse replace(Long id, CategoryRequest request);

    void delete(Long id);
}
