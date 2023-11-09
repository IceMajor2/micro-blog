package com.demo.blog.blogpostservice.post.dto;

import com.demo.blog.blogpostservice.author.dto.AuthorResponse;
import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.category.assertion.dto.CategoryResponseAssert;
import com.demo.blog.blogpostservice.category.dto.CategoryResponse;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.demo.blog.blogpostservice.author.datasupply.AuthorDataSupply.ANY_AUTHOR;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.CONCURRENCY_CATEGORY;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.CONTAINERS_CATEGORY;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.DOCKER_POST;
import static org.assertj.core.api.Assertions.assertThat;

class PostResponseTest {

    @Test
    void shouldCorrectlyMapPost() {
        // arrange
        long expectedId = DOCKER_POST.getId().longValue();
        String expectedTitle = new String(DOCKER_POST.getTitle());
        String expectedBody = new String(DOCKER_POST.getBody());
        String expectedPublishedDate = new String(DOCKER_POST.getPublishedOn().toString());
        String expectedUpdatedDate = new String(DOCKER_POST.getUpdatedOn().toString());
        PostResponse expected = new PostResponse(expectedId, expectedTitle, null,
                null, expectedPublishedDate, expectedUpdatedDate, expectedBody);

        // act
        PostResponse actual = new PostResponse(DOCKER_POST);

        // assert
        assertThat(actual)
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "title", "body", "publishedOn", "updatedOn")
                .isEqualTo(expected);
    }

    @Test
    void shouldCorrectlyMapAuthor() {
        // arrange
        String expectedName = new String(ANY_AUTHOR.getUsername());
        AuthorResponse expectedAuthor = new AuthorResponse(expectedName);

        // act
        PostResponse actual = new PostResponse(DOCKER_POST, ANY_AUTHOR, Collections.emptyList());

        // assert
        assertThat(actual.author()).isEqualTo(expectedAuthor);
    }

    @Test
    void shouldCorrectlyMapCategories() {
        // arrange
        List<Category> categories = List.of(CONTAINERS_CATEGORY, CONCURRENCY_CATEGORY);
        List<CategoryResponse> expectedCategories = List.of(
                new CategoryResponse(CONTAINERS_CATEGORY.getId().longValue(), new String(CONTAINERS_CATEGORY.getName())),
                new CategoryResponse(CONCURRENCY_CATEGORY.getId().longValue(), new String(CONCURRENCY_CATEGORY.getName()))
        );

        // act
        PostResponse actual = new PostResponse(DOCKER_POST, ANY_AUTHOR, categories);

        // assert
        assertThat(actual.categories())
                .isSortedAccordingTo(CategoryResponseAssert.CATEGORY_RESPONSE_COMPARATOR)
                .containsExactlyInAnyOrderElementsOf(expectedCategories);
    }
}