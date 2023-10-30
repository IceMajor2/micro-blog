package com.demo.blog.postservice.category.integration;

import com.demo.blog.postservice.category.dto.CategoryRequest;
import com.demo.blog.postservice.category.dto.CategoryResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static com.demo.blog.postservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.postservice.category.Constants.API_CATEGORY_ID;
import static com.demo.blog.postservice.util.RestRequestUtils.put;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
@TestMethodOrder(MethodOrderer.Random.class)
public class CategoryHttpPutTest {

    @ParameterizedTest
    @MethodSource("com.demo.blog.postservice.category.datasupply.CategoryDataSupply#validRequests")
    @DirtiesContext
    void shouldReplaceEntryOnValidRequest(CategoryRequest request) {
        // arrange
        Long idToReplace = 5L;
        long expectedId = idToReplace.longValue();
        String expectedName = new String(request.name());
        CategoryResponse expected = new CategoryResponse(expectedId, expectedName);

        // act
        var actual = put(API_CATEGORY_ID, request, CategoryResponse.class, idToReplace);

        // assert
        assertThat(actual).isValidPutResponse(expected);
    }
}
