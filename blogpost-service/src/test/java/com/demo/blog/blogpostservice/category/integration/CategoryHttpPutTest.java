package com.demo.blog.blogpostservice.category.integration;

import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.category.CategoryBuilder;
import com.demo.blog.blogpostservice.category.CategoryRepository;
import com.demo.blog.blogpostservice.category.dto.CategoryRequest;
import com.demo.blog.blogpostservice.category.dto.CategoryResponse;
import com.demo.blog.blogpostservice.config.BaseIntegrationTest;
import com.demo.blog.blogpostservice.exception.ApiExceptionDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThatException;
import static com.demo.blog.blogpostservice.category.Constants.*;
import static com.demo.blog.blogpostservice.util.RestRequestUtils.put;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
@TestMethodOrder(MethodOrderer.Random.class)
public class CategoryHttpPutTest extends BaseIntegrationTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @ParameterizedTest
    @MethodSource("com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply#validCategoryRequests")
    @DirtiesContext
    void shouldReplaceEntryOnValidRequest(CategoryRequest request) {
        // arrange
        Long replaceId = 5L;
        CategoryResponse expected = new CategoryResponse(replaceId, request.name());
        Category expectedDb = new CategoryBuilder().fromResponse(expected).build();

        // act
        var actual = put(API_CATEGORY_ID, request, CategoryResponse.class, replaceId);

        // assert
        assertThat(actual).isValidPutResponse(expected);
        assertThat(categoryRepository).persisted(expectedDb);
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
    @MethodSource("com.demo.blog.blogpostservice.datasupply.StringDataSupply#blankStrings")
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
    @MethodSource("com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply#tooLongCategoryNames")
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
    @MethodSource("com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply#justRightLengthCategoryNames")
    void shouldAcceptCorrectCategoryName(String validLongCategoryName) {
        // arrange
        Long replaceId = 1L;
        CategoryRequest request = new CategoryRequest(validLongCategoryName);
        CategoryResponse expected = new CategoryResponse(replaceId, request.name());
        Category expectedDb = new CategoryBuilder().fromResponse(expected).build();

        // act
        var actual = put(API_CATEGORY_ID, request, CategoryResponse.class, replaceId);

        // assert
        assertThat(actual).isValidPutResponse(expected);
        assertThat(categoryRepository).persisted(expectedDb);
    }
}
