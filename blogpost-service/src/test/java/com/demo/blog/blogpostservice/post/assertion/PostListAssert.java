package com.demo.blog.blogpostservice.post.assertion;

import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.datasupply.PostConstants;
import org.assertj.core.api.AbstractListAssert;
import org.assertj.core.api.Assertions;
import org.springframework.data.util.Streamable;

import java.util.List;
import java.util.stream.Collectors;

public class PostListAssert extends AbstractListAssert<PostListAssert, List<Post>, Post, PostAssert> {

    @Override
    protected PostAssert toAssert(Post actual, String description) {
        return PostAssert.assertThat(actual);
    }

    @Override
    protected PostListAssert newAbstractIterableAssert(Iterable<? extends Post> actualIterable) {
        return assertThat(Streamable.of(actualIterable).stream().collect(Collectors.toList()));
    }

    public PostListAssert(List<Post> actual) {
        super(actual, PostListAssert.class);
    }

    public static PostListAssert assertThat(List<Post> actual) {
        return new PostListAssert(actual);
    }

    public PostListAssert isSortedByNewest() {
        isNotNull();
        Assertions.assertThat(actual)
                .isSortedAccordingTo(PostConstants.PUBLISHED_DESC_COMPARATOR);
        return this;
    }
}
