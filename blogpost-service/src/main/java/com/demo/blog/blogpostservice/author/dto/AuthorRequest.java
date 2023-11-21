package com.demo.blog.blogpostservice.author.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record AuthorRequest(
        @NotBlank(message = "Author username must be specified")
        String username
) {
}
