package com.demo.blog.blogpostservice.post.builder;

import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.post.Post;

import java.time.LocalDateTime;

public interface PostOptionalFieldsBuilder {

    PostOptionalFieldsBuilder withId(long id);

    PostOptionalFieldsBuilder updatedOn(LocalDateTime timestamp);

    PostOptionalFieldsBuilder updatedNow();
    PostUpdatedOnBuilder updated();

    PostOptionalFieldsBuilder withCategories(Category... categories);

    Post build();
}
