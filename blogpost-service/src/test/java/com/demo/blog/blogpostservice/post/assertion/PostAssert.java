package com.demo.blog.blogpostservice.post.assertion;

import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.postcategory.PostCategory;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.springframework.data.util.Streamable;

import java.time.LocalDateTime;
import java.util.List;

public class PostAssert extends AbstractAssert<PostAssert, Post> {

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

    public PostAssert updatedOn(LocalDateTime expected) {
        isNotNull();
        Assertions.assertThat(actual.getUpdatedOn())
                .as("updatedOn")
                .hasYear(expected.getYear())
                .hasMonth(expected.getMonth())
                .hasDayOfMonth(expected.getDayOfMonth())
                .hasHour(expected.getHour())
                .hasMinute(expected.getMinute());
        return this;
    }

    public PostAssert categorizedAs(Iterable<Category> expected) {
        isNotNull();
        List<PostCategory> expectedPostCategories = Streamable.of(expected)
                .map(category -> new PostCategory(null, category.getId(), actual.getId()))
                .toList();
        Assertions.assertThat(actual.getCategories())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "postId")
                .containsExactlyInAnyOrderElementsOf(expectedPostCategories);
        return this;
    }

    public PostAssert writtenBy(Long expectedAuthorId) {
        isNotNull();
        Assertions.assertThat(actual.getAuthor().getId())
                .isEqualTo(expectedAuthorId);
        return this;
    }
}
