package com.demo.blog.blogpostservice.category.integration;

import com.demo.blog.blogpostservice.category.CategoryRepository;
import com.demo.blog.blogpostservice.category.dto.CategoryResponse;
import com.demo.blog.blogpostservice.config.BaseIntegrationTest;
import com.demo.blog.blogpostservice.exception.ApiExceptionDTO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Streamable;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.*;
import static com.demo.blog.blogpostservice.category.Constants.*;
import static com.demo.blog.blogpostservice.util.RestRequestUtils.get;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
@TestClassOrder(ClassOrderer.Random.class)
public class CategoryHttpGetTest extends BaseIntegrationTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Nested
    @TestMethodOrder(MethodOrderer.Random.class)
    class GetById {

        @ParameterizedTest
        @ValueSource(longs = {1L, 3L})
        void shouldReturnCategoryOnGetId(Long id) {
            // arrange
            CategoryResponse expected = new CategoryResponse(categoryRepository.findById(id).get());

            // act
            var actual = get(API_CATEGORY_ID, CategoryResponse.class, id);

            // assert
            assertThatCategoryRestResponse(actual).isValidGetResponse(expected);
        }

        @ParameterizedTest
        @ValueSource(longs = {-35784L, 0L, 128411L})
        void shouldThrowExceptionOnIdNotFound(long id) {
            // act
            var actual = get(API_CATEGORY_ID, ApiExceptionDTO.class, id);

            // assert
            assertThatException(actual)
                    .isNotFound()
                    .withMessage(ID_NOT_FOUND_MSG_T.formatted(id))
                    .withPath(API_CATEGORY_SLASH + id);
        }
    }

    @Nested
    @TestMethodOrder(MethodOrderer.Random.class)
    class GetByName {

        @Test
        void shouldReturnCategoryOnGetByName() {
            // arrange
            CategoryResponse expected = new CategoryResponse(categoryRepository.findById(4L).get());
            String categoryName = expected.name();

            // act
            var actual = get(API_CATEGORY_NAME, CategoryResponse.class, categoryName);

            // assert
            assertThatCategoryRestResponse(actual).isValidGetResponse(expected);
        }

        @ParameterizedTest
        @MethodSource("com.demo.blog.blogpostservice.datasupply.StringDataSupply#blankStrings")
        void shouldThrowExceptionOnCategoryNameMissing(String blankCategoryName) {
            // act
            var actual = get(API_CATEGORY_NAME, ApiExceptionDTO.class, blankCategoryName);

            // assert
            assertThatException(actual)
                    .isBadRequest()
                    .withMessage(BLANK_MSG)
                    .withPath(API_CATEGORY);
        }

        @ParameterizedTest
        @MethodSource("com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply#validCategoryNames")
        void shouldThrowExceptionOnCategoryNotFound(String notExistingCategory) {
            // act
            var actual = get(API_CATEGORY_NAME, ApiExceptionDTO.class, notExistingCategory);

            // assert
            assertThatException(actual)
                    .isNotFound()
                    .withMessage(NAME_NOT_FOUND_MSG_T.formatted(notExistingCategory))
                    .withPath(API_CATEGORY);
        }
    }

    @Nested
    @TestMethodOrder(MethodOrderer.Random.class)
    class GetCollection {

        @Test
        void shouldReturnAllCategoriesInAlphabeticOrder() {
            // arrange
            List<CategoryResponse> expected = Streamable.of(categoryRepository.findByOrderByNameAsc())
                    .stream()
                    .map(CategoryResponse::new)
                    .toList();

            // act
            var actual = get(API_CATEGORY, PARAMETERIZED_TYPE_REFERENCE);

            // assert
            assertThatCategoriesRestResponse(actual).isValidGetAllResponse(expected);
        }
    }
}
