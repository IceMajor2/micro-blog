package com.demo.blog.postservice.category;

import com.demo.blog.postservice.category.dto.CategoryResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;

import static com.demo.blog.postservice.assertions.AllAssertions.assertThat;
import static com.demo.blog.postservice.util.RestRequestUtil.get;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.sql.init.mode=never")
@TestClassOrder(ClassOrderer.Random.class)
public class CategoryIntegrationTest {

    @Autowired
    CategoryRepository categoryRepository;

    private static final String API_CATEGORY = "/api/category";
    private static final String API_CATEGORY_ID = "/api/category/{id}";

    @Nested
    @TestMethodOrder(MethodOrderer.Random.class)
    class GetRequests {

        @ParameterizedTest
        @ValueSource(longs = {1L, 3L})
        void shouldReturnCategoryOnGetId(Long id) {
            // arrange
            Category expected = getCategory(id);

            // act
            var actual = get(API_CATEGORY_ID, HttpMethod.GET, CategoryResponse.class, id);

            // assert
            assertThat(actual).isValidGetResponse(expected);
        }
    }

    private Category getCategory(long id) {
        return categoryRepository.findById(id).get();
    }
}
