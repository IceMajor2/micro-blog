package com.demo.blog.blogpostservice.post.builder.fluent;

import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.post.Post;

public interface PostOptionalFieldsBuilder {

    PostOptionalFieldsBuilder withId(long id);

    PostUpdatedOnOptions updated();

    PostOptionalFieldsBuilder withCategories(Category... categories);

    Post build();
}
