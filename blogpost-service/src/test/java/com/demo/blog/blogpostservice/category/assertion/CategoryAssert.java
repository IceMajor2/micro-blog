package com.demo.blog.blogpostservice.category.assertion;

import com.demo.blog.blogpostservice.category.Category;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import java.util.Comparator;

public class CategoryAssert extends AbstractAssert<CategoryAssert, Category> {

    public static final Comparator<Category> CATEGORY_COMPARATOR =
            Comparator.comparing(Category::getName);

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

    public CategoryAssert hasId(Long expected) {
        isNotNull();
        Assertions.assertThat(actual.getId())
                .as("id")
                .isEqualTo(expected);
        return this;
    }
}
