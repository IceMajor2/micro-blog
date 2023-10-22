package com.demo.blog.postservice.category;

import com.demo.blog.postservice.category.dto.CategoryResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static com.demo.blog.postservice.assertions.AllAssertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.sql.init.mode=never")
@TestClassOrder(ClassOrderer.Random.class)
public class CategoryIntegrationTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    CategoryRepository categoryRepository;

    @Nested
    @TestMethodOrder(MethodOrderer.Random.class)
    class GetRequests {

        @ParameterizedTest
        @ValueSource(longs = {1L, 3L})
        void shouldReturnCategoryOnGetId(Long id) {
            // arrange
            Category expected = getCategory(id);

            // act
            ResponseEntity<CategoryResponse> actual = testRestTemplate
                    .exchange("/api/category/" + id, HttpMethod.GET, null, CategoryResponse.class);

            // assert
            assertThat(actual).isValidGetResponse(expected);
        }
    }

    private Category getCategory(long id) {
        return categoryRepository.findById(id).get();
    }
}
