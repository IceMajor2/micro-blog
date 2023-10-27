package com.demo.blog.postservice.category;

import com.demo.blog.postservice.category.dto.CategoryRequest;
import com.demo.blog.postservice.category.dto.CategoryResponse;
import com.demo.blog.postservice.exception.ApiExceptionDTO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.util.Streamable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static com.demo.blog.postservice.assertions.AllAssertions.*;
import static com.demo.blog.postservice.category.CategoryRequestTest.NAME_TOO_LONG_MSG;
import static com.demo.blog.postservice.category.CategoryRequestTest.NOT_BLANK_MSG;
import static com.demo.blog.postservice.util.CategoryTestRepository.*;
import static com.demo.blog.postservice.util.RestRequestUtil.get;
import static com.demo.blog.postservice.util.RestRequestUtil.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
@TestClassOrder(ClassOrderer.Random.class)
public class CategoryIntegrationTest {

    private static final String API_CATEGORY = "/api/category";
    private static final String API_CATEGORY_ID = "/api/category/{id}";
    private static final String API_CATEGORY_SLASH = API_CATEGORY + '/';

    public static final String CATEGORY_EXISTS_MSG_TEMPL = "Category '%s' already exists";
    public static final String CATEGORY_NOT_FOUND_MSG_TEMPL = "Category of '%d' ID was not found";

    private static final ParameterizedTypeReference<Iterable<CategoryResponse>> PARAMETERIZED_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {};

    @Nested
    @TestMethodOrder(MethodOrderer.Random.class)
    class GetRequests {

        @ParameterizedTest
        @ValueSource(longs = {1L, 3L})
        void shouldReturnCategoryOnGetId(Long id) {
            // arrange
            CategoryResponse expected = new CategoryResponse(getCategory(id));

            // act
            var actual = get(API_CATEGORY_ID, CategoryResponse.class, id);

            // assert
            assertThat(actual).isValidGetResponse(expected);
        }

        @Test
        void shouldReturnAllCategoriesOnGetAll() {
            // arrange
            Iterable<CategoryResponse> expected = Streamable.of(getAllCategoriesSorted()).map(CategoryResponse::new);

            // act
            var actual = get(API_CATEGORY, PARAMETERIZED_TYPE_REFERENCE);

            // assert
            assertThatCategories(actual).areValidGetAllResponse(expected);
        }

        @ParameterizedTest
        @ValueSource(longs = {-35784L, 0L, 128411L})
        void shouldThrowExceptionOnIdNotFound(long id) {
            // act
            var actual = get(API_CATEGORY_ID, ApiExceptionDTO.class, id);

            // assert
            assertThatException(actual)
                    .isNotFound()
                    .withMessage(CATEGORY_NOT_FOUND_MSG_TEMPL.formatted(id))
                    .withPath(API_CATEGORY_SLASH + id);
        }
    }

    @Nested
    @TestMethodOrder(MethodOrderer.Random.class)
    class PostRequests {

        @ParameterizedTest
        @MethodSource("com.demo.blog.postservice.category.CategoryDataSupply#validRequests")
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
        @ValueSource(strings = {" \t \n  ", "", "       "})
        @NullSource
        void shouldThrowExceptionOnNameBlank(String invalidCategoryName) {
            // arrange
            CategoryRequest request = new CategoryRequest(invalidCategoryName);

            // act
            var actual = post(API_CATEGORY, request, ApiExceptionDTO.class);

            // assert
            assertThatException(actual)
                    .isBadRequest()
                    .withMessage(NOT_BLANK_MSG)
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
                    .withMessage(CATEGORY_EXISTS_MSG_TEMPL.formatted(existingCategoryName))
                    .withPath(API_CATEGORY);
        }

        @ParameterizedTest
        @MethodSource("com.demo.blog.postservice.category.CategoryDataSupply#tooLongRequestNames")
        void shouldThrowExceptionOnCategoryNameTooLong(String tooLongCategoryName) {
            // arrange
            CategoryRequest request = new CategoryRequest(tooLongCategoryName);

            // act
            var actual = post(API_CATEGORY, request, ApiExceptionDTO.class);

            // assert
            assertThatException(actual)
                    .isBadRequest()
                    .withMessage(NAME_TOO_LONG_MSG)
                    .withPath(API_CATEGORY);
        }
    }
}
