package com.demo.blog.postservice.category.integration;

import com.demo.blog.postservice.category.CategoryRepository;
import com.demo.blog.postservice.category.dto.CategoryResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static com.demo.blog.postservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.postservice.category.datasupply.Constants.API_CATEGORY_ID;
import static com.demo.blog.postservice.util.RestRequestUtils.delete;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
@TestMethodOrder(MethodOrderer.Random.class)
public class CategoryHttpDeleteTest {

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
}
