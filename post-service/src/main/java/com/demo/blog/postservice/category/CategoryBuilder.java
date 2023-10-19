package com.demo.blog.postservice.category;

import com.demo.blog.postservice.category.dto.CategoryRequest;

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
        this.name = request.getName();
        return this;
    }

    public Category build() {
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        return category;
    }
}
