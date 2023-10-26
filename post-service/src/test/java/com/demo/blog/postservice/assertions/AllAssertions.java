package com.demo.blog.postservice.assertions;

import com.demo.blog.postservice.category.dto.CategoryResponse;
import com.demo.blog.postservice.exception.ApiExceptionDTO;
import jakarta.validation.ConstraintViolation;
import org.assertj.core.api.Assertions;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public class AllAssertions extends Assertions {

    public static CategoryAssert assertThat(CategoryResponse actual) {
        return CategoryAssert.assertThat(actual);
    }

    public static CategoryResponseEntityAssert assertThat(ResponseEntity<CategoryResponse> actual) {
        return CategoryResponseEntityAssert.assertThat(actual);
    }

    public static CategoryResponseEntityAssert.CategoriesResponseEntityAssert assertThatCategories(ResponseEntity<Iterable<CategoryResponse>> actual) {
        return CategoryResponseEntityAssert.CategoriesResponseEntityAssert.assertThat(actual);
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
