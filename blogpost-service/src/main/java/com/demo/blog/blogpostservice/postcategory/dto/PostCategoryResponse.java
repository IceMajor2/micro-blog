package com.demo.blog.blogpostservice.postcategory.dto;

import java.util.List;

public record PostCategoryResponse(
        String postTitle,
        List<String> categoryNames
) {
}
