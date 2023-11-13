package com.demo.blog.blogpostservice.post.builder;

import java.time.LocalDateTime;

public interface PostPublishedOnBuilder {

    PostOptionalFieldsBuilder publishedOn(LocalDateTime timestamp);

    PostOptionalFieldsBuilder publishedNow();

    PostOptionalFieldsBuilder publishedFifteenMinsAgo();

    PostOptionalFieldsBuilder publishedThirtyMinsAgo();

    PostOptionalFieldsBuilder publishedHourAgo();
}
