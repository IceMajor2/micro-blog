package com.demo.blog.postservice.category.dto;

import com.demo.blog.postservice.category.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;

public record CategoryResponse(
        @JsonIgnore Long id,
        String name
) {
    public CategoryResponse(Category category) {
        this(category.getId(), category.getName());
    }
}
