package com.demo.blog.blogpostservice.category.assertion.dto;

import com.demo.blog.blogpostservice.category.dto.CategoryResponse;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import java.util.Comparator;

public class CategoryResponseAssert extends AbstractAssert<CategoryResponseAssert, CategoryResponse> {

    public static final Comparator<CategoryResponse> CATEGORY_RESPONSE_COMPARATOR = Comparator.comparing(CategoryResponse::name);

    public CategoryResponseAssert(CategoryResponse actual) {
        super(actual, CategoryResponseAssert.class);
    }

    public static CategoryResponseAssert assertThat(CategoryResponse actual) {
        return new CategoryResponseAssert(actual);
    }

    public CategoryResponseAssert isNamed(String name) {
        isNotNull();
        Assertions.assertThat(actual.name())
                .as("name")
                .isEqualTo(name);
        return this;
    }

    public CategoryResponseAssert hasId(Long expected) {
        isNotNull();
        Assertions.assertThat(actual.id())
                .as("id")
                .isEqualTo(expected);
        return this;
    }
}
