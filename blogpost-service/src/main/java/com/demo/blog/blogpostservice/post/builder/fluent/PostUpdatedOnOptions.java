package com.demo.blog.blogpostservice.post.builder.fluent;

import java.time.LocalDateTime;

public interface PostUpdatedOnOptions extends PostOptionalFieldsBuilder {

    PostOptionalFieldsBuilder at(LocalDateTime timestamp);

    PostOptionalFieldsBuilder justNow();
}
