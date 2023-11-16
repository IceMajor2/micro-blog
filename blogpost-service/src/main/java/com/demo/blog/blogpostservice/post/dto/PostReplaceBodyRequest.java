package com.demo.blog.blogpostservice.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostBodyRequest(
        @NotNull(message = "Post must be specified")
        Long postId,
        @NotBlank(message = "Body must be specified")
        String body
) {
}
