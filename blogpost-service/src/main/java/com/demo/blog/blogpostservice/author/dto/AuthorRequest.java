package com.demo.blog.blogpostservice.author.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record AuthorRequest(
        @NotBlank(message = "Author username must be specified")
        @Length(min = 5, message = "Author username must be at least 5 characters long")
        @Length(max = 32, message = "Author username cannot exceed 32 characters")
        String username
) {
}
