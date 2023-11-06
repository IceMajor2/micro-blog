package com.demo.blog.blogpostservice.post.assertion;

import com.demo.blog.blogpostservice.post.dto.PostResponse;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import java.time.LocalDateTime;
import java.util.Comparator;

public class PostAssert extends AbstractAssert<PostAssert, PostResponse> {

    public static final Comparator<PostResponse> PUBLISHED_ON_COMPARATOR = (post1, post2) -> {
        LocalDateTime date1 = LocalDateTime.parse(post1.publishedOn());
        LocalDateTime date2 = LocalDateTime.parse(post2.publishedOn());
        return date2.compareTo(date1);
    };

    public PostAssert(PostResponse actual) {
        super(actual, PostAssert.class);
    }

    public static PostAssert assertThat(PostResponse actual) {
        return new PostAssert(actual);
    }

    public PostAssert hasId(long expected) {
        isNotNull();
        Assertions.assertThat(actual.id())
                .as("id")
                .isEqualTo(expected);
        return this;
    }

    public PostAssert hasTitle(String expected) {
        isNotNull();
        Assertions.assertThat(actual.title())
                .as("title")
                .isEqualTo(expected);
        return this;
    }

    public PostAssert hasBody(String expected) {
        isNotNull();
        Assertions.assertThat(actual.body())
                .as("body")
                .isEqualTo(expected);
        return this;
    }
}
