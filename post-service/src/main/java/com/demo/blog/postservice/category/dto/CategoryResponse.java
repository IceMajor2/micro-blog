package com.demo.blog.postservice.category.dto;

import com.demo.blog.postservice.category.Category;
import lombok.Builder;

@Builder
public record CategoryResponse(
        String name
) {
    public CategoryResponse(Category category) {
        this(category.getName());
    }
}
