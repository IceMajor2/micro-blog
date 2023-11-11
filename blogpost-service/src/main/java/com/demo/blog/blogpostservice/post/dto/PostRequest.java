package com.demo.blog.blogpostservice.post.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record PostRequest(
        @NotBlank(message = "Post title must be specified")
        @Length(min = 5, message = "Post title must be at least 5 character long")
        @Length(max = 255, message = "Post title cannot exceed 255 characters")
        String title,
        @NotBlank(message = "Body must be specified")
        String body
) {}
