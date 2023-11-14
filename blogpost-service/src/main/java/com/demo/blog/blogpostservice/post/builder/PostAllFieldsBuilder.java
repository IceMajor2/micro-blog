package com.demo.blog.blogpostservice.post.builder;

import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.post.Post;

import java.time.LocalDateTime;
import java.util.List;

public interface PostAllFieldsBuilder {

    PostAllFieldsBuilder withId(long postId);

    PostAllFieldsBuilder withTitle(String title);

    PostAllFieldsBuilder withBody(String body);

    PostAllFieldsBuilder writtenBy(long authorId);

    PostAllFieldsBuilder published(LocalDateTime on);

    PostAllFieldsBuilder updated(LocalDateTime on);

    PostAllFieldsBuilder withCategories(Category... categories);

    Post build();

    PostAllFieldsBuilder clearCategories();

    PostAllFieldsBuilder replacingCategories(List<Category> categories);
}
