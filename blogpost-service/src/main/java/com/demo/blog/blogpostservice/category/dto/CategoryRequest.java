package com.demo.blog.blogpostservice.category.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CategoryRequest(
        @NotBlank(message = "Category name must be specified")
        @Length(max = 32, message = "Category name cannot exceed 32 characters")
        String name
) {
}
