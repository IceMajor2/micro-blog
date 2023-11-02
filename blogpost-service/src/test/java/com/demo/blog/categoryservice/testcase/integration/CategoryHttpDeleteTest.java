package com.demo.blog.categoryservice.testcase.integration;

import com.demo.blog.categoryservice.dto.CategoryResponse;
import com.demo.blog.categoryservice.exception.ApiExceptionDTO;
import com.demo.blog.categoryservice.repository.CategoryRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import static com.demo.blog.categoryservice.environment.assertion.AllAssertions.assertThat;
import static com.demo.blog.categoryservice.environment.assertion.AllAssertions.assertThatException;
import static com.demo.blog.categoryservice.environment.datasupply.category.Constants.*;
import static com.demo.blog.categoryservice.environment.util.RestRequestUtils.delete;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
@TestMethodOrder(MethodOrderer.Random.class)
public class CategoryHttpDeleteTest {

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

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DirtiesContext
    void shouldDeleteCategoryFromDatabase() {
        // arrange
        Long deleteId = 5L;
        Long expectedSize = categoryRepository.count() - 1;

        // act
        var actual = delete(API_CATEGORY_ID, CategoryResponse.class, deleteId);
        Long actualSize = categoryRepository.count();

        // assert
        assertThat(actual).isValidDeleteResponse();
        assertThat(actualSize)
                .withFailMessage("Category was not deleted from the repository")
                .isEqualTo(expectedSize);
    }

    @ParameterizedTest
    @ValueSource(longs = {-10, 0, 1000})
    void shouldThrowExceptionOnDeletingIdNotFound(Long deleteId) {
        // act
        var actual = delete(API_CATEGORY_ID, ApiExceptionDTO.class, deleteId);

        // assert
        assertThatException(actual)
                .isNotFound()
                .withMessage(ID_NOT_FOUND_MSG_T.formatted(deleteId))
                .withPath(API_CATEGORY_SLASH + deleteId);
    }
}
