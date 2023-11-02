package com.demo.blog.blogpostservice.category;

import com.demo.blog.blogpostservice.category.dto.CategoryRequest;
import com.demo.blog.blogpostservice.category.dto.CategoryResponse;

public class CategoryBuilder {

    private Long id;
    private String name;

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

    public CategoryBuilder copy(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        return this;
    }
}
