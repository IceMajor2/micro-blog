package com.demo.blog.blogpostservice.category;

import com.demo.blog.blogpostservice.category.dto.CategoryRequest;
import com.demo.blog.blogpostservice.category.dto.CategoryResponse;
import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.postcategory.PostCategory;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CategoryBuilder {

    private Long id;
    private String name;
    private Set<PostCategory> posts = new HashSet<>();

    public CategoryBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public CategoryBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CategoryBuilder fromRequest(CategoryRequest request) {
        this.name = request.name();
        return this;
    }

    public CategoryBuilder fromResponse(CategoryResponse response) {
        this.id = response.id();
        this.name = response.name();
        return this;
    }

    public Category build() {
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        return category;
    }

    public CategoryBuilder addPost(Post post) {
        final PostCategory postCategory = new PostCategory();
        postCategory.setPostId(AggregateReference.to(post.getId()));
        this.posts.add(postCategory);
        return this;
    }

    public CategoryBuilder addPosts(Post... posts) {
        Arrays.stream(posts).forEach(this::addPost);
        return this;
    }
}
