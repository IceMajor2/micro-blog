package com.demo.blog.postservice.assertions;

import com.demo.blog.postservice.category.Category;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class CategoryAssert extends AbstractAssert<CategoryAssert, Category> {

    public CategoryAssert(Category actual) {
        super(actual, CategoryAssert.class);
    }

    public static CategoryAssert assertThat(Category actual) {
        return new CategoryAssert(actual);
    }

    public CategoryAssert isNamed(String name) {
        isNotNull();
        Assertions.assertThat(actual.getName())
                .as("name")
                .isEqualTo(name);
        return this;
    }
}
