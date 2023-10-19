package com.demo.blog.postservice.category.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CategoryRequest(
        @NotBlank(message = "Name must be specified") String name
) {
}
