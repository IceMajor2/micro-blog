package com.demo.blog.blogpostservice.post.dto;

import org.junit.jupiter.api.Test;

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
}