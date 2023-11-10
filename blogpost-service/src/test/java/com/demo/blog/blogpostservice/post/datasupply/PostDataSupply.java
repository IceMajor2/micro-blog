package com.demo.blog.blogpostservice.post.datasupply;

import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostBuilder;
import com.demo.blog.blogpostservice.post.dto.PostRequest;

import java.util.stream.Stream;

import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.CONTAINERS_CATEGORY;

public class PostDataSupply {

    public static final Post DOCKER_POST = new PostBuilder()
            .withId(1L)
            .withAuthor(10L)
            .withTitle("Dockerizing a Spring Boot application")
            .withBody("Step 1. Install Docker")
            .withCategories(CONTAINERS_CATEGORY)
            .publishedThirtyMinsAgo()
            .updatedNow()
            .build();
    public static final Post SPRING_POST = new PostBuilder()
            .withId(2L)
            .withAuthor(3L)
            .withTitle("Spring & Spring Boot: what's the difference?")
            .withBody("Oftentimes, both of the terms are used interchangeably. However, ...")
            .publishedHourAgo()
            .build();
    public static final PostRequest SPRING_POST_REQUEST = new PostRequest(
            "Spring & Spring Boot: what's the difference?",
            "Oftentimes, both of the terms are used interchangeably. However, ..."
    );
    public static final PostRequest DOCKER_POST_REQUEST = new PostRequest(
            "Dockerizing a Spring Boot application",
            "Step 1. Install Docker"
    );

    public static Stream<PostRequest> validPostRequests() {
        return Stream.of(
                SPRING_POST_REQUEST,
                DOCKER_POST_REQUEST
        );
    }

    public static Stream<Post> validPostsSortedByPublishedOnDesc() {
        return Stream.of(
                DOCKER_POST,
                SPRING_POST
        );
    }
}
