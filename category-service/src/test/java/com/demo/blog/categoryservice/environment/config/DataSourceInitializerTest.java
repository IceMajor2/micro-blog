package com.demo.blog.categoryservice.environment.config;

import com.demo.blog.categoryservice.model.Category;
import com.demo.blog.categoryservice.builder.CategoryBuilder;
import com.demo.blog.categoryservice.repository.CategoryRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
@TestClassOrder(ClassOrderer.Random.class)
public class DataSourceInitializerTest {

    static MySQLContainer mysql = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.33"));

    static {
        mysql.start();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        System.out.println("HGBDSFHJKSDF!");
        dynamicPropertyRegistry.add("spring.datasource.url", mysql::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", mysql::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", mysql::getPassword);
    }

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
