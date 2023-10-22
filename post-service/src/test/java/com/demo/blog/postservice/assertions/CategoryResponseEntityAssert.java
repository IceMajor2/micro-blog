package com.demo.blog.postservice.assertions;

import com.demo.blog.postservice.category.Category;
import com.demo.blog.postservice.category.dto.CategoryResponse;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class CategoryResponseEntityAssert extends AbstractAssert<CategoryResponseEntityAssert, ResponseEntity<CategoryResponse>> {

    public CategoryResponseEntityAssert(ResponseEntity<CategoryResponse> actual) {
        super(actual, CategoryResponseEntityAssert.class);
    }

    public static CategoryResponseEntityAssert assertThat(ResponseEntity<CategoryResponse> actual) {
        return new CategoryResponseEntityAssert(actual);
    }

    public CategoryResponseEntityAssert statusCodeIs(HttpStatusCode statusCode) {
        isNotNull();
        Assertions.assertThat(actual.getStatusCode())
                .as("status code")
                .isEqualTo(statusCode);
        return this;
    }

    public CategoryResponseEntityAssert matchesModel(Category category) {
        isNotNull();
        CategoryAssert.assertThat(actual.getBody()).isNamed(category.getName());
        return this;
    }

    public CategoryResponseEntityAssert isValidGetResponse(Category category) {
        statusCodeIs(HttpStatus.OK);
        matchesModel(category);
        return this;
    }
}
