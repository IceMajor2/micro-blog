package com.demo.blog.blogpostservice.post.builder;

import java.time.LocalDateTime;

public interface PostUpdatedOnBuilder extends PostOptionalFieldsBuilder {

    PostOptionalFieldsBuilder updatedOn(LocalDateTime timestamp);

    PostOptionalFieldsBuilder updatedNow();
}
