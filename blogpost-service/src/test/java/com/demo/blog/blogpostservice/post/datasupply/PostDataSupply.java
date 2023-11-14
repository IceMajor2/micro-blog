package com.demo.blog.blogpostservice.post.datasupply;

import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.dto.PostBodyRequest;
import com.demo.blog.blogpostservice.post.dto.PostRequest;

import java.util.stream.Stream;

import static com.demo.blog.blogpostservice.author.datasupply.AuthorDataSupply.ANY_AUTHOR;
import static com.demo.blog.blogpostservice.author.datasupply.AuthorDataSupply.JOHN_SMITH;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.CONTAINERS_CATEGORY;

public class PostDataSupply {

    public static final Post DOCKER_POST = Post.PostFluentBuilder.post()
            .withTitle("Dockerizing a Spring Boot application")
            .withBody("Step 1. Install Docker")
            .writtenBy(ANY_AUTHOR.getId())
            .published().thirtyMinsAgo()
            .withId(1L)
            .withCategories(CONTAINERS_CATEGORY)
            .build();

    public static final Post SPRING_POST = Post.PostFluentBuilder.post()
            .withTitle("Spring & Spring Boot: what's the difference?")
            .withBody("Oftentimes, both of the terms are used interchangeably. However, ...")
            .writtenBy(JOHN_SMITH.getId())
            .published().hourAgo()
            .withId(2L)
            .build();

    public static final PostRequest SPRING_POST_REQUEST = new PostRequest(
            new String(SPRING_POST.getTitle()),
            new String(SPRING_POST.getBody())
    );
    public static final PostRequest DOCKER_POST_REQUEST = new PostRequest(
            new String(DOCKER_POST.getTitle()),
            new String(DOCKER_POST.getBody())
    );
    public static final PostBodyRequest NEW_DOCKER_BODY_REQUEST = new PostBodyRequest(
            DOCKER_POST.getId().longValue(), "This post has been updated with guidelines for the new Spring Boot"
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

    public static Stream<String> lessThanFiveCharactersTitles() {
        return Stream.of(
                "FOUR",
                "333",
                "TW"
        );
    }

    public static Stream<String> justMoreOrEqualThanFiveCharactersTitles() {
        return Stream.of(
                "JUG_1",
                "Java21",
                "How-to?"
        );
    }

    public static Stream<String> moreThan255CharactersTitles() {
        return Stream.of(
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. " +
                "Aenean commodo ligula eget dolor. Aenean massa. Cum sociis " +
                "natoque penatibus et magnis dis parturient montes, nascetur " +
                "ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis,.",
                "A wonderful serenity has taken possession of my entire soul, like " +
                "these sweet mornings of spring which I enjoy with my whole heart. " +
                "I am alone, and feel the charm of existence in this spot, " +
                "which was created for the bliss of souls like mine. I am so happy,."
        );
    }

    public static Stream<String> justLessOrEqualThan255CharactersTitles() {
        return Stream.of(
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. " +
                "Aenean commodo ligula eget dolor. Aenean massa. Cum sociis " +
                "natoque penatibus et magnis dis parturient montes, nascetur " +
                "ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis",
                "A wonderful serenity has taken possession of my entire soul, like " +
                "these sweet mornings of spring which I enjoy with my whole heart. " +
                "I am alone, and feel the charm of existence in this spot, " +
                "which was created for the bliss of souls like mine. I am so happy"
        );
    }
}
