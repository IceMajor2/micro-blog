package com.demo.blog.postservice.category;

import com.demo.blog.postservice.category.dto.CategoryResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.demo.blog.postservice.assertions.AllAssertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.sql.init.mode=never")
public class CategoryIntegrationTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void shouldReturnCategoryOnGetId() {
        ResponseEntity<CategoryResponse> actual = testRestTemplate
                .exchange("/api/category/1", HttpMethod.GET, null, CategoryResponse.class);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).isNamed(categoryRepository.findById(1L).get().getName());
    }
}
