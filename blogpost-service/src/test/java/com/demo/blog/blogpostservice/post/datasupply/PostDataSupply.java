package com.demo.blog.blogpostservice.post.datasupply;

import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostBuilder;

import java.util.stream.Stream;

import static com.demo.blog.blogpostservice.category.Constants.ANY_LONG;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.CONTAINERS_CATEGORY;

public class PostDataSupply {

    public static final Post DOCKER_POST = new PostBuilder()
            .withId(1L)
            .withAuthor(ANY_LONG)
            .withTitle("Dockerizing a Spring Boot application")
            .withBody("Step 1. Install Docker")
            .withCategories(CONTAINERS_CATEGORY)
            .publishedNow()
            .build();

    public static Stream<Post> validPostsSortedByPublishedOnDesc() {
        return Stream.of(
                new PostBuilder()
                        .withId(1L)
                        .withAuthor(1L)
                        .withTitle("How to build microservices")
                        .withBody("Some body...")
                        .publishedNow()
                        .build(),
                new PostBuilder()
                        .withId(2L)
                        .withAuthor(1L)
                        .withTitle("How is ArrayList built under the hood")
                        .withBody("Body of the post...")
                        .publishedFifteenMinsAgo()
                        .build(),
                new PostBuilder()
                        .withId(3L)
                        .withAuthor(2L)
                        .withTitle("Learning Python...")
                        .withBody("Text that is a blog post's body...")
                        .publishedHourAgo()
                        .build()
        );
    }
}
