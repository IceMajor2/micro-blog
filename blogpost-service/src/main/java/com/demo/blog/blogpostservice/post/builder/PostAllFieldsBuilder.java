package com.demo.blog.blogpostservice.post.builder;

import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.post.Post;

import java.time.LocalDateTime;
import java.util.Collection;

public interface PostAllFieldsBuilder {

    PostAllFieldsBuilder withId(long postId);

    PostAllFieldsBuilder withTitle(String title);

    PostAllFieldsBuilder withBody(String body);

    PostAllFieldsBuilder writtenBy(long authorId);

    PostAllFieldsBuilder published(LocalDateTime on);

    PostAllFieldsBuilder updated(LocalDateTime on);

    PostAllFieldsBuilder addCategories(Category... categories);

    PostAllFieldsBuilder addCategories(Collection<Category> categories);

    PostAllFieldsBuilder setCategories(Category... category);

    PostAllFieldsBuilder setCategories(Collection<Category> categories);

    PostAllFieldsBuilder clearCategories();

    Post build();
}
