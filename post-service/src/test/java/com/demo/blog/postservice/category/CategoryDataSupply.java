package com.demo.blog.postservice.category;

import com.demo.blog.postservice.category.dto.CategoryRequest;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.demo.blog.postservice.category.CategoryServiceTest.ANY_LONG;

public class CategoryDataSupply {

    static Set<Category> toSet(Stream<Category> stream) {
        return stream.collect(Collectors.toCollection(LinkedHashSet::new));
    }

    static Stream<Category> sortedCategories() {
        return categories()
                .sorted(Comparator.comparing(Category::getName));

    }

    static Stream<Category> categories() {
        return Stream.of(
                new CategoryBuilder().withId(ANY_LONG).withName("Java").build(),
                new CategoryBuilder().withId(ANY_LONG).withName("Threads").build(),
                new CategoryBuilder().withId(ANY_LONG).withName("Security").build(),
                new CategoryBuilder().withId(ANY_LONG).withName("Microservices").build(),
                new CategoryBuilder().withId(ANY_LONG).withName("Project Management").build()
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
