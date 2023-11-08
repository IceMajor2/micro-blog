package com.demo.blog.blogpostservice.category;

import com.demo.blog.blogpostservice.category.dto.CategoryResponse;
import com.demo.blog.blogpostservice.config.BaseIntegrationTest;
import com.demo.blog.blogpostservice.exception.ApiExceptionDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.*;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryConstants.*;
import static com.demo.blog.blogpostservice.util.RestRequestUtils.delete;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
@TestMethodOrder(MethodOrderer.Random.class)
public class CategoryControllerHttpDeleteIT extends BaseIntegrationTest {

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
        assertThatCategoryRestResponse(actual).isValidDeleteResponse();
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
