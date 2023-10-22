package com.demo.blog.postservice.assertions;

import com.demo.blog.postservice.category.dto.CategoryResponse;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class CategoryAssert extends AbstractAssert<CategoryAssert, CategoryResponse> {

    public CategoryAssert(CategoryResponse actual) {
        super(actual, CategoryAssert.class);
    }

    public static CategoryAssert assertThat(CategoryResponse actual) {
        return new CategoryAssert(actual);
    }

    public CategoryAssert isNamed(String name) {
        isNotNull();
        Assertions.assertThat(actual.name())
                .as("name")
                .isEqualTo(name);
        return this;
    }
}
