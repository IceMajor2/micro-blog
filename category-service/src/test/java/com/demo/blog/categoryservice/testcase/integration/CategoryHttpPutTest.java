package com.demo.blog.categoryservice.testcase.integration;

import com.demo.blog.categoryservice.model.Category;
import com.demo.blog.categoryservice.repository.CategoryRepository;
import com.demo.blog.categoryservice.dto.CategoryRequest;
import com.demo.blog.categoryservice.dto.CategoryResponse;
import com.demo.blog.categoryservice.exception.ApiExceptionDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static com.demo.blog.categoryservice.environment.assertion.AllAssertions.assertThat;
import static com.demo.blog.categoryservice.environment.assertion.AllAssertions.assertThatException;
import static com.demo.blog.categoryservice.environment.datasupply.category.Constants.*;
import static com.demo.blog.categoryservice.environment.util.RestRequestUtils.put;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
@TestMethodOrder(MethodOrderer.Random.class)
public class CategoryHttpPutTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @ParameterizedTest
    @MethodSource("com.demo.blog.categoryservice.environment.datasupply.category.CategoryDataSupply#validRequests")
    @DirtiesContext
    void shouldReplaceEntryOnValidRequest(CategoryRequest request) {
        // arrange
        Long replaceId = 5L;
        CategoryResponse expected = new CategoryResponse(replaceId, request.name());

        // act
        var actual = put(API_CATEGORY_ID, request, CategoryResponse.class, replaceId);
        CategoryResponse dbActual = new CategoryResponse(categoryRepository.findById(replaceId).get());

        // assert
        assertThat(actual).isValidPutResponse(expected);
        assertThat(dbActual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(longs = {-523897, 0, 12392})
    void shouldThrowExceptionOnIdNotFound(Long nonExistingId) {
        // arrange
        CategoryRequest request = new CategoryRequest("Databases");

        // act
        var actual = put(API_CATEGORY_ID, request, ApiExceptionDTO.class, nonExistingId);

        // assert
        assertThatException(actual)
                .isNotFound()
                .withMessage(ID_NOT_FOUND_MSG_T.formatted(nonExistingId))
                .withPath(API_CATEGORY_SLASH + nonExistingId);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2})
    void shouldThrowExceptionWhenCategoryAlreadyExists(Long replaceId) {
        // arrange
        Category alreadyPersistedCategory = categoryRepository.findById(1L).get();
        CategoryRequest request = new CategoryRequest(alreadyPersistedCategory.getName());

        // act
        var actual = put(API_CATEGORY_ID, request, ApiExceptionDTO.class, replaceId);

        // assert
        assertThatException(actual)
                .isConflict()
                .withMessage(EXISTS_MSG_T.formatted(alreadyPersistedCategory.getName()))
                .withPath(API_CATEGORY_SLASH + replaceId);
    }

    @ParameterizedTest
    @MethodSource("com.demo.blog.categoryservice.environment.datasupply.StringDataSupply#blankStrings")
    void shouldThrowExceptionOnCategoryNameBlank(String blankCategoryName) {
        // arrange
        Long replaceId = 3L;
        CategoryRequest request = new CategoryRequest(blankCategoryName);

        // act
        var actual = put(API_CATEGORY_ID, request, ApiExceptionDTO.class, replaceId);

        // assert
        assertThatException(actual)
                .isBadRequest()
                .withMessage(BLANK_MSG)
                .withPath(API_CATEGORY_SLASH + replaceId);
    }

    @ParameterizedTest
    @MethodSource("com.demo.blog.categoryservice.environment.datasupply.category.CategoryDataSupply#tooLongRequestNames")
    void shouldThrowExceptionOnCategoryNameTooLong(String tooLongCategoryName) {
        // arrange
        Long replaceId = 1L;
        CategoryRequest request = new CategoryRequest(tooLongCategoryName);

        // act
        var actual = put(API_CATEGORY_ID, request, ApiExceptionDTO.class, replaceId);

        // assert
        assertThatException(actual)
                .isBadRequest()
                .withMessage(TOO_LONG_MSG)
                .withPath(API_CATEGORY_SLASH + replaceId);
    }

    @ParameterizedTest
    @DirtiesContext
    @MethodSource("com.demo.blog.categoryservice.environment.datasupply.category.CategoryDataSupply#justRightLengthRequestName")
    void shouldAcceptCorrectCategoryName(String validLongCategoryName) {
        // arrange
        Long replaceId = 1L;
        CategoryRequest request = new CategoryRequest(validLongCategoryName);
        CategoryResponse expected = new CategoryResponse(replaceId, request.name());

        // act
        var actual = put(API_CATEGORY_ID, request, CategoryResponse.class, replaceId);
        CategoryResponse dbActual = new CategoryResponse(categoryRepository.findById(replaceId).get());

        // assert
        assertThat(actual).isValidPutResponse(expected);
        assertThat(dbActual).isEqualTo(expected);
    }
}
