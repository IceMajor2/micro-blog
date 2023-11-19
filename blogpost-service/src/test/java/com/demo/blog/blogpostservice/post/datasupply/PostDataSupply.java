package com.demo.blog.blogpostservice.post.datasupply;

import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.dto.PostChangeTitleRequest;
import com.demo.blog.blogpostservice.post.dto.PostReplaceBodyRequest;
import com.demo.blog.blogpostservice.post.dto.PostRequest;

import java.util.stream.Stream;

import static com.demo.blog.blogpostservice.author.datasupply.AuthorDataSupply.ANY_AUTHOR;
import static com.demo.blog.blogpostservice.author.datasupply.AuthorDataSupply.JOHN_SMITH;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.*;

public class PostDataSupply {

    public static final Post DOCKER_POST = Post.PostBuilder.post()
            .withTitle("Dockerizing a Spring Boot application")
            .withBody("Step 1. Install Docker")
            .writtenBy(ANY_AUTHOR.getId())
            .published().thirtyMinsAgo()
            .withId(1L)
            .categories().addCategories(CONTAINERS_CATEGORY)
            .build();

    public static final Post SPRING_POST = Post.PostBuilder.post()
            .withTitle("Spring & Spring Boot: what's the difference?")
            .withBody("Oftentimes, both of the terms are used interchangeably. However, ...")
            .writtenBy(JOHN_SMITH.getId())
            .published().hourAgo()
            .withId(2L)
            .categories().setCategories(JAVA_CATEGORY, SPRING_CATEGORY)
            .build();

    public static final Post ENGINEERING_POST = Post.PostBuilder.post()
            .withTitle("Deep dive into creational design patterns")
            .withBody("Knowing a programming language is important, but a good developer should also...")
            .writtenBy(JOHN_SMITH.getId())
            .published().fifteenMinsAgo()
            .withId(3L)
            .build();

    public static final Post JAVA_POST = Post.PostBuilder.post()
            .withTitle("Java as first programming language")
            .withBody("The never-ending question: which should be my first programming language?")
            .writtenBy(ANY_AUTHOR.getId())
            .published().now()
            .withId(4L)
            .categories().setCategories(JAVA_CATEGORY)
            .build();

    public static final PostRequest SPRING_POST_REQUEST = new PostRequest(
            new String(SPRING_POST.getTitle()),
            new String(SPRING_POST.getBody())
    );
    public static final PostRequest DOCKER_POST_REQUEST = new PostRequest(
            new String(DOCKER_POST.getTitle()),
            new String(DOCKER_POST.getBody())
    );
    public static final PostReplaceBodyRequest NEW_DOCKER_BODY_REQUEST = new PostReplaceBodyRequest(
            "This post has been updated with guidelines for the new Spring Boot"
    );
    public static final PostChangeTitleRequest NEW_SPRING_TITLE_REQUEST = new PostChangeTitleRequest(
            "Spring and Spring Boot: what's the difference?"
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
