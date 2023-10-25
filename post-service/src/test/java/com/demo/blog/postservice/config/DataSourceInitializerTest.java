package com.demo.blog.postservice.config;

import com.demo.blog.postservice.category.Category;
import com.demo.blog.postservice.category.CategoryBuilder;
import com.demo.blog.postservice.category.CategoryRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.sql.init.mode=never")
@TestClassOrder(ClassOrderer.Random.class)
public class DataSourceInitializerTest {

    @Nested
    @TestMethodOrder(MethodOrderer.Random.class)
    class CategoryData {

        @Autowired
        private CategoryRepository categoryRepository;

        private static final Set<Category> EXPECTED_CATEGORIES = Set.of(
                new CategoryBuilder().withId(1L).withName("Spring").build(),
                new CategoryBuilder().withId(2L).withName("Concurrency").build(),
                new CategoryBuilder().withId(3L).withName("Testing").build(),
                new CategoryBuilder().withId(4L).withName("Software Engineering").build(),
                new CategoryBuilder().withId(5L).withName("C").build(),
                new CategoryBuilder().withId(6L).withName("Low-level").build()
        );

        @Test
        void dataSqlShouldLoadCategories() {
            Iterable<Category> actualCategories = categoryRepository.findAll();
            assertThat(actualCategories).containsAll(EXPECTED_CATEGORIES);
        }
    }
}