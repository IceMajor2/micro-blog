package com.demo.blog.blogpostservice.post.assertion.domain;

import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.postcategory.PostCategory;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import java.util.Comparator;

public class PostAssert extends AbstractAssert<PostAssert, Post> {

    public static final Comparator<Post> PUBLISHED_ON_COMPARATOR = Comparator.comparing(Post::getPublishedOn).reversed();

    public PostAssert(Post actual) {
        super(actual, PostAssert.class);
    }

    public static PostAssert assertThat(Post actual) {
        return new PostAssert(actual);
    }

    public PostAssert hasId(long expected) {
        isNotNull();
        Assertions.assertThat(actual.getId())
                .as("id")
                .isEqualTo(expected);
        return this;
    }

    public PostAssert isTitled(String expected) {
        isNotNull();
        Assertions.assertThat(actual.getTitle())
                .as("title")
                .isEqualTo(expected);
        return this;
    }

    public PostAssert hasBody(String expected) {
        isNotNull();
        Assertions.assertThat(actual.getBody())
                .as("body")
                .isEqualTo(expected);
        return this;
    }

    public PostAssert categorizedAs(Iterable<PostCategory> expected) {
        isNotNull();
        Assertions.assertThat(actual.getCategories())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(expected);
        return this;
    }

    public PostAssert writtenBy(Long expectedAuthorId) {
        isNotNull();
        Assertions.assertThat(actual.getAuthor().getId())
                .isEqualTo(expectedAuthorId);
        return this;
    }

    public PostAssert isFieldByFieldEqualTo(Post expected) {
        isNotNull();
        hasId(expected.getId());
        isTitled(expected.getTitle());
        hasBody(expected.getTitle());
        categorizedAs(expected.getCategories());
        writtenBy(expected.getAuthor().getId());
        return this;
    }
}
