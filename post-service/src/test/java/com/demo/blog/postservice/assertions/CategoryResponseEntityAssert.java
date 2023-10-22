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

    public CategoryResponseEntityAssert statusCodeIs(HttpStatusCode expectedStatusCode) {
        isNotNull();
        Assertions.assertThat(actual.getStatusCode())
                .as("status code")
                .isEqualTo(expectedStatusCode);
        return this;
    }

    /**
     * Asserts field of domain model (i.e. {@code Category} object)
     * are equal to the ones of the response DTO
     * (i.e. {@code CategoryResponse} object).
     * @param expected
     */
    public CategoryResponseEntityAssert matchesModel(Category expected) {
        isNotNull();
        CategoryAssert.assertThat(actual.getBody()).isNamed(expected.getName());
        return this;
    }

    /**
     * A custom assertion that checks for the average HTTP GET request's response.
     * Namely, it asserts the response's status code is {@code 200 OK} and that
     * the {@code expected} object matches actual (through {@link CategoryResponseEntityAssert#matchesModel}).
     * @param expected expected {@code Category} object
     */
    public CategoryResponseEntityAssert isValidGetResponse(Category expected) {
        statusCodeIs(HttpStatus.OK);
        matchesModel(expected);
        return this;
    }
}
