package com.demo.blog.blogpostservice.post.assertion;

import com.demo.blog.blogpostservice.post.dto.PostResponse;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import java.time.LocalDateTime;
import java.util.Comparator;

public class PostResponseAssert extends AbstractAssert<PostResponseAssert, PostResponse> {

    public static final Comparator<PostResponse> PUBLISHED_ON_COMPARATOR = (post1, post2) -> {
        LocalDateTime date1 = LocalDateTime.parse(post1.publishedOn());
        LocalDateTime date2 = LocalDateTime.parse(post2.publishedOn());
        return date2.compareTo(date1);
    };

    public PostResponseAssert(PostResponse actual) {
        super(actual, PostResponseAssert.class);
    }

    public static PostResponseAssert assertThat(PostResponse actual) {
        return new PostResponseAssert(actual);
    }

    public PostResponseAssert hasId(long expected) {
        isNotNull();
        Assertions.assertThat(actual.id())
                .as("id")
                .isEqualTo(expected);
        return this;
    }

    public PostResponseAssert hasTitle(String expected) {
        isNotNull();
        Assertions.assertThat(actual.title())
                .as("title")
                .isEqualTo(expected);
        return this;
    }

    public PostResponseAssert hasBody(String expected) {
        isNotNull();
        Assertions.assertThat(actual.body())
                .as("body")
                .isEqualTo(expected);
        return this;
    }
}
