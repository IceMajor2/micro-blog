package com.demo.blog.blogpostservice.post.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record PostRequest(
        @NotBlank(message = "Post title must be specified")
        @Min(value = 5, message = "Post title must be at least 5 character long")
        @Max(value = 255, message = "Post title cannot exceed 255 characters")
        String title,
        @NotBlank(message = "Body must be specified")
        String body
) {}
