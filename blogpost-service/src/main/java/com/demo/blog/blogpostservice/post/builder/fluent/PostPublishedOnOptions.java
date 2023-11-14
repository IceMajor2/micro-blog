package com.demo.blog.blogpostservice.post.builder.fluent;

import java.time.LocalDateTime;

public interface PostPublishedOnOptions {

    PostOptionalFieldsBuilder on(LocalDateTime timestamp);

    PostOptionalFieldsBuilder now();

    PostOptionalFieldsBuilder fifteenMinsAgo();

    PostOptionalFieldsBuilder thirtyMinsAgo();

    PostOptionalFieldsBuilder hourAgo();
}
