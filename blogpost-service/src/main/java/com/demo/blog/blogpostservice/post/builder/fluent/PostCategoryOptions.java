package com.demo.blog.blogpostservice.post.builder.fluent;

import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.postcategory.PostCategory;

import java.util.Set;

public interface PostCategoryOptions {

    PostOptionalFieldsBuilder addCategories(Category... categories);

    PostOptionalFieldsBuilder setCategories(Category... categories);

    PostOptionalFieldsBuilder setCategories(Set<PostCategory> categories);

    PostOptionalFieldsBuilder clearCategories();
}
