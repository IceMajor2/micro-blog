package com.demo.blog.categoryservice.testcase.integration;

import com.demo.blog.categoryservice.repository.CategoryRepository;
import com.demo.blog.categoryservice.dto.CategoryRequest;
import com.demo.blog.categoryservice.dto.CategoryResponse;
import com.demo.blog.categoryservice.exception.ApiExceptionDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static com.demo.blog.categoryservice.environment.assertion.AllAssertions.*;
import static com.demo.blog.categoryservice.environment.datasupply.category.Constants.*;
import static com.demo.blog.categoryservice.environment.util.RestRequestUtils.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
@TestMethodOrder(MethodOrderer.Random.class)
public class CategoryHttpPostTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @ParameterizedTest
    @MethodSource("com.demo.blog.categoryservice.environment.datasupply.category.CategoryDataSupply#validRequests")
    @DirtiesContext
    void shouldAddEntryOnValidRequest(CategoryRequest request) {
        // arrange
        Long expectedSize = categoryRepository.count() + 1;
        CategoryResponse expected = new CategoryResponse(expectedSize, request.name());

        // act
        var actual = post(API_CATEGORY, request, CategoryResponse.class);
        Long actualSize = categoryRepository.count();
        CategoryResponse dbActual = new CategoryResponse(categoryRepository.findById(expectedSize).get());

        // assert
        assertThat(actual).isValidPostResponse(expected);
        assertThat(actualSize).isEqualTo(expectedSize);
        assertThat(dbActual).isEqualTo(expected);
    }

    @Test
    @DirtiesContext
    void shouldReturnLocationHeaderAndCreatedStatusCodeOnSuccessfulPost() {
        // arrange
        long expectedSize = categoryRepository.count() + 1L;
        String expectedLocation = "/api/category/" + (expectedSize);

        CategoryRequest request = new CategoryRequest("Pascal");

        // act
        var actual = post(API_CATEGORY, request, CategoryResponse.class);

        // assert
        assertThatResponse(actual)
                .statusCodeIsCreated()
                .locationHeaderContains(expectedLocation);
    }

    @ParameterizedTest
    @MethodSource("com.demo.blog.categoryservice.environment.datasupply.StringDataSupply#blankStrings")
    @NullSource
    void shouldThrowExceptionOnNameBlank(String invalidCategoryName) {
        // arrange
        CategoryRequest request = new CategoryRequest(invalidCategoryName);

        // act
        var actual = post(API_CATEGORY, request, ApiExceptionDTO.class);

        // assert
        assertThatException(actual)
                .isBadRequest()
                .withMessage(BLANK_MSG)
                .withPath(API_CATEGORY);
    }

    @Test
    void shouldThrowExceptionOnCategoryAlreadyExists() {
        // arrange
        String existingCategoryName = categoryRepository.findById(2L).get().getName();
        CategoryRequest request = new CategoryRequest(existingCategoryName);

        // act
        var actual = post(API_CATEGORY, request, ApiExceptionDTO.class);

        // assert
        assertThatException(actual)
                .isConflict()
                .withMessage(EXISTS_MSG_T.formatted(existingCategoryName))
                .withPath(API_CATEGORY);
    }

    @ParameterizedTest
    @MethodSource("com.demo.blog.categoryservice.environment.datasupply.category.CategoryDataSupply#tooLongRequestNames")
    void shouldThrowExceptionOnCategoryNameTooLong(String tooLongCategoryName) {
        // arrange
        CategoryRequest request = new CategoryRequest(tooLongCategoryName);

        // act
        var actual = post(API_CATEGORY, request, ApiExceptionDTO.class);

        // assert
        assertThatException(actual)
                .isBadRequest()
                .withMessage(TOO_LONG_MSG)
                .withPath(API_CATEGORY);
    }
}
