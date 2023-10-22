package com.demo.blog.postservice.category;

import com.demo.blog.postservice.category.dto.CategoryRequest;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.demo.blog.postservice.category.CategoryServiceTest.ANY_LONG;

public class CategoryDataSupply {

    static Set<Category> sortedCategories() {
        return categoryNames()
                .map(name -> new CategoryBuilder()
                        .withId(ANY_LONG)
                        .withName(name)
                        .build())
                .sorted(Comparator.comparing(Category::getName))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

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
