package com.demo.blog.blogpostservice.category.datasupply;

import com.demo.blog.blogpostservice.category.CategoryBuilder;
import com.demo.blog.blogpostservice.category.dto.CategoryRequest;
import com.demo.blog.blogpostservice.category.Category;

import java.util.Comparator;
import java.util.stream.Stream;

public class CategoryDataSupply {

    public static final CategoryRequest THREADS_CATEGORY_REQUEST = new CategoryRequest("Threads");
    public static final CategoryRequest CONCURRENCY_CATEGORY_REQUEST = new CategoryRequest("Concurrency");
    public static final Category THREADS_CATEGORY = new CategoryBuilder()
            .withId(1L)
            .withName("Threads")
            .build();
    public static final Category CONCURRENCY_CATEGORY = new CategoryBuilder()
            .withId(2L)
            .withName("Concurrency")
            .build();
    public static final Category CONTAINERS_CATEGORY = new CategoryBuilder()
            .withId(3L)
            .withName("Containers")
            .build();
    public static final Category SPRING_CATEGORY = new CategoryBuilder()
            .withId(4L)
            .withName("Spring")
            .build();
    public static final Category JAVA_CATEGORY = new CategoryBuilder()
            .withId(5L)
            .withName("Java")
            .build();

    public static Stream<Category> sortedValidCategories() {
        return validCategories()
                .sorted(Comparator.comparing(Category::getName));
    }

    public static Stream<Category> validCategories() {
        return Stream.of(
                new CategoryBuilder().withId(1L).withName("Java").build(),
                new CategoryBuilder().withId(1L).withName("Threads").build(),
                new CategoryBuilder().withId(1L).withName("Security").build(),
                new CategoryBuilder().withId(1L).withName("Microservices").build(),
                new CategoryBuilder().withId(1L).withName("Project Management").build()
        );
    }

    static Stream<CategoryRequest> validCategoryRequests() {
        return Stream.of(
                new CategoryRequest("Python"),
                new CategoryRequest("Backend"),
                new CategoryRequest("Algorithms"),
                new CategoryRequest("Procedural Programming Languages"),
                new CategoryRequest("Data Structures")
        );
    }

    static Stream<String> validCategoryNames() {
        return Stream.of(
                "Quality Assurance",
                "Unit testing",
                "Integration testing",
                "Project-oriented learning"
        );
    }

    static Stream<String> tooLongCategoryNames() {
        return Stream.of(
                "THIRTY_THREE_CHARS_STRING_REQUEST",
                "_THIRTY_MORE_CHARS_STRING_REQUEST_",
                "_FORTY_MORE_MORE_CHARS_CHARS_STRING_REQUEST__"
        );
    }

    static Stream<String> justRightLengthCategoryNames() {
        return Stream.of(
                "32_CHARS_STRING_REQUEST_ACCEPTED",
                "31_CHAR_STRING_REQUEST_ACCEPTED"
        );
    }
}
