package com.demo.blog.postservice.assertions;

import com.demo.blog.postservice.category.dto.CategoryResponse;
import jakarta.validation.ConstraintViolation;
import org.assertj.core.api.Assertions;

import java.util.Set;

public class AllAssertions extends Assertions {

    public static CategoryAssert assertThat(CategoryResponse actual) {
        return CategoryAssert.assertThat(actual);
    }

    public static JakartaValidationAssert assertThat(Set<ConstraintViolation<Object>> actual) {
        return JakartaValidationAssert.assertThat(actual);
    }
}
