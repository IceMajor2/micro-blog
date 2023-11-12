package com.demo.blog.blogpostservice.postcategory.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PostCategoryRequest(
        @NotNull(message = "Post must be specified")
        Long postId,
        @NotNull(message = "At least one category must be specified")
        @Size(min = 1, message = "At least one category must be specified")
        List<Long> categoryIds
) {
}
