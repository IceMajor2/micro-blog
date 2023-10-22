package com.demo.blog.postservice.category;

import com.demo.blog.postservice.category.dto.CategoryRequest;
import com.demo.blog.postservice.category.dto.CategoryResponse;

import java.util.Set;

interface CategoryService {

    CategoryResponse getById(Long id);

    CategoryResponse getByName(String name);

    Set<CategoryResponse> getAll();

    CategoryResponse add(CategoryRequest request);

    CategoryResponse replace(CategoryRequest request);

    CategoryResponse delete(Long id);
}
