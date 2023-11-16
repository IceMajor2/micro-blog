package com.demo.blog.blogpostservice.post.dto;

import jakarta.validation.constraints.NotBlank;

public record PostChangeTitleRequest(
        @NotBlank(message = "Post title must be specified") String newTitle
) {
}
