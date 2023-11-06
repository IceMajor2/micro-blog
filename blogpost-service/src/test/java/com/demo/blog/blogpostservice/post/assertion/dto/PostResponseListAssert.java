package com.demo.blog.blogpostservice.post.assertion.dto;

import com.demo.blog.blogpostservice.post.dto.PostResponse;
import org.assertj.core.api.AbstractListAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ListAssert;
import org.springframework.data.util.Streamable;

import java.util.List;
import java.util.stream.Collectors;

public class PostResponseListAssert extends AbstractListAssert<PostResponseListAssert, List<PostResponse>, PostResponse, PostResponseAssert> {

    @Override
    protected PostResponseAssert toAssert(PostResponse actual, String description) {
        return PostResponseAssert.assertThat(actual);
    }

    @Override
    protected PostResponseListAssert newAbstractIterableAssert(Iterable<? extends PostResponse> actualIterable) {
        return assertThat(Streamable.of(actualIterable).stream().collect(Collectors.toList()));
    }

    public PostResponseListAssert(List<PostResponse> actual) {
        super(actual, PostResponseListAssert.class);
    }

    public static PostResponseListAssert assertThat(List<PostResponse> actual) {
        return new PostResponseListAssert(actual);
    }

    public PostResponseListAssert isSortedByNewest() {
        Assertions.assertThat(actual)
                .isSortedAccordingTo(PostResponseAssert.PUBLISHED_ON_COMPARATOR);
        return this;
    }

    public ListAssert<PostResponse> ignoringDateFields() {
        return Assertions.assertThat(actual)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("publishedOn", "updatedOn");
    }
}
