package com.demo.blog.postservice.category;

import com.demo.blog.postservice.category.dto.CategoryRequest;
import com.demo.blog.postservice.post.Post;

import java.util.HashSet;
import java.util.Set;

public class CategoryBuilder {

    private Long id;
    private String name;
    private Set<Post> posts = new HashSet<>();

    public CategoryBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public CategoryBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CategoryBuilder withPosts(Set<Post> posts) {
        this.posts = posts;
        return this;
    }

    public CategoryBuilder fromRequest(CategoryRequest request) {
        this.name = request.getName();
        return this;
    }

    public Category build() {
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        category.setPosts(posts);
        return category;
    }
}
