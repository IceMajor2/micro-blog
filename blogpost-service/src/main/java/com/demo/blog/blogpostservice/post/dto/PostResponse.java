package com.demo.blog.blogpostservice.post.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record PostResponse(
        @JsonIgnore Long id,
        String title,
        String body
) {
}
