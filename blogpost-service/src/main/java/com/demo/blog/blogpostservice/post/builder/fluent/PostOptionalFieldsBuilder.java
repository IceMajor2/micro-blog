package com.demo.blog.blogpostservice.post.builder.fluent;

import com.demo.blog.blogpostservice.post.Post;

public interface PostOptionalFieldsBuilder {

    PostOptionalFieldsBuilder withId(long id);

    PostUpdatedOnOptions updated();

    PostCategoryOptions categories();

//    PostOptionalFieldsBuilder addCategories(Category... categories);
//
//    PostOptionalFieldsBuilder withCategories(Category... categories);
//
//    PostOptionalFieldsBuilder withCategories(Set<PostCategory> categories);

    Post build();
}
