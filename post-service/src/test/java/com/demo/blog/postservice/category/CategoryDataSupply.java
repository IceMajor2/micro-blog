package com.demo.blog.postservice.category;

import com.demo.blog.postservice.category.dto.CategoryRequest;

import java.util.stream.Stream;

public class CategoryDataSupply {

    static Stream<String> categoryNames() {
        return Stream.of(
                "Java",
                "Threads",
                "Security",
                "Microservices",
                "Project Management"
        );
    }

    static Stream<CategoryRequest> validRequests() {
        return Stream.of(
                CategoryRequest.builder().name("Python").build(),
                CategoryRequest.builder().name("Backend").build(),
                CategoryRequest.builder().name("Algorithms").build(),
                CategoryRequest.builder().name("Data Structures").build()
        );
    }
}
