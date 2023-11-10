package com.demo.blog.blogpostservice.postcategory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record PostCategoryRequest(
        @NotBlank(message = "Post must be specified") Long postId,
        @Min(value = 1, message = "At least one category must be specified") List<Long> categoryIds
) {
}
