package com.demo.blog.postservice.category.assertion;

import com.demo.blog.postservice.category.dto.CategoryResponse;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import java.util.Comparator;

public class CategoryAssert extends AbstractAssert<CategoryAssert, CategoryResponse> {

    public static final Comparator<CategoryResponse> CATEGORY_RESPONSE_COMPARATOR = Comparator.comparing(CategoryResponse::name);

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

    public CategoryAssert hasId(Long expected) {
        isNotNull();
        Assertions.assertThat(actual.id())
                .as("id")
                .isEqualTo(expected);
        return this;
    }
}
