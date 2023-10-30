package com.demo.blog.postservice.category.integration;

import com.demo.blog.postservice.category.dto.CategoryRequest;
import com.demo.blog.postservice.category.dto.CategoryResponse;
import com.demo.blog.postservice.exception.ApiExceptionDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static com.demo.blog.postservice.assertion.AllAssertions.*;
import static com.demo.blog.postservice.category.datasupply.Constants.*;
import static com.demo.blog.postservice.category.component.CategoryTestRepository.getCategoriesSize;
import static com.demo.blog.postservice.category.component.CategoryTestRepository.getCategory;
import static com.demo.blog.postservice.util.RestRequestUtils.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
@TestMethodOrder(MethodOrderer.Random.class)
public class CategoryHttpPostTest {

    @ParameterizedTest
    @MethodSource("com.demo.blog.postservice.category.datasupply.CategoryDataSupply#validRequests")
    @DirtiesContext
    void shouldAddEntryOnValidRequest(CategoryRequest request) {
        // arrange
        CategoryResponse expected = new CategoryResponse(getCategoriesSize() + 1, new String(request.name()));

        // act
        var actual = post(API_CATEGORY, request, CategoryResponse.class);

        // assert
        assertThat(actual).isValidPostResponse(expected);
    }

    @Test
    @DirtiesContext
    void shouldReturnLocationHeaderAndCreatedStatusCodeOnSuccessfulPost() {
        // arrange
        String expectedLocation = "/api/category/" + (getCategoriesSize() + 1);
        CategoryRequest request = new CategoryRequest("Pascal");

        // act
        var actual = post(API_CATEGORY, request, CategoryResponse.class);

        // assert
        assertThatResponse(actual)
                .statusCodeIsCreated()
                .locationHeaderContains(expectedLocation);
    }

    @ParameterizedTest
    @MethodSource("com.demo.blog.postservice.datasupply.StringDataSupply#blankStrings")
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
        String existingCategoryName = getCategory(2L).getName();
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
    @MethodSource("com.demo.blog.postservice.category.datasupply.CategoryDataSupply#tooLongRequestNames")
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
