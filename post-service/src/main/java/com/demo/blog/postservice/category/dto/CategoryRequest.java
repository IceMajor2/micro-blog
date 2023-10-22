package com.demo.blog.postservice.category.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record CategoryRequest(
        @NotBlank(message = "Name must be specified")
        @Length(max = 32, message = "Category name cannot exceed 32 characters")
        String name
) {
}
