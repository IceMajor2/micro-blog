package com.demo.blog.categoryservice.environment.assertion;

import com.demo.blog.categoryservice.dto.CategoryResponse;
import com.demo.blog.categoryservice.environment.assertion.category.CategoryAssert;
import com.demo.blog.categoryservice.environment.assertion.category.CategoryRepositoryAssert;
import com.demo.blog.categoryservice.environment.assertion.category.CategoryRestAssert;
import com.demo.blog.categoryservice.exception.ApiExceptionDTO;
import com.demo.blog.categoryservice.repository.CategoryRepository;
import jakarta.validation.ConstraintViolation;
import org.assertj.core.api.Assertions;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public class AllAssertions extends Assertions {

    public static CategoryAssert assertThat(CategoryResponse actual) {
        return CategoryAssert.assertThat(actual);
    }

    public static CategoryRestAssert assertThat(ResponseEntity<CategoryResponse> actual) {
        return CategoryRestAssert.assertThat(actual);
    }

    public static CategoryRestAssert.CategoryIterableRestAssert assertThatCategories(ResponseEntity<Iterable<CategoryResponse>> actual) {
        return CategoryRestAssert.CategoryIterableRestAssert.assertThat(actual);
    }

    public static CategoryRepositoryAssert assertThat(CategoryRepository actual) {
        return CategoryRepositoryAssert.assertThat(actual);
    }

    public static HttpResponseAssert assertThatResponse(ResponseEntity<?> actual) {
        return HttpResponseAssert.assertThat(actual);
    }

    public static RestExceptionAssert assertThatException(ResponseEntity<ApiExceptionDTO> actual) {
        return RestExceptionAssert.assertThatException(actual);
    }

    public static JakartaValidationAssert assertThat(Set<ConstraintViolation<Object>> actual) {
        return JakartaValidationAssert.assertThat(actual);
    }
}
