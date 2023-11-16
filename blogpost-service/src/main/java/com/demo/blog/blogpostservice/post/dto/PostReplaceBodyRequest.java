package com.demo.blog.blogpostservice.post.dto;

import jakarta.validation.constraints.NotBlank;

public record PostReplaceBodyRequest(
//        @NotNull(message = "Post must be specified")
//        Long postId,
        @NotBlank(message = "Body must be specified")
        String body
) {
}
