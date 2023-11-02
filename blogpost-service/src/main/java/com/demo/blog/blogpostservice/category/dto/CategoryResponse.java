package com.demo.blog.blogpostservice.category.dto;

import com.demo.blog.blogpostservice.category.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public record CategoryResponse(
        @JsonIgnore Long id,
        String name
) {
    public CategoryResponse(Category category) {
        this(category.getId(), category.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryResponse)) return false;
        CategoryResponse that = (CategoryResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id    );
    }
}
