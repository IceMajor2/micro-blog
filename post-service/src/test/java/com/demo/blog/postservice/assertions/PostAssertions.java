package com.demo.blog.postservice.assertions;

import com.demo.blog.postservice.category.Category;
import jakarta.validation.ConstraintViolation;
import org.assertj.core.api.Assertions;

import java.util.Set;

public class PostAssertions extends Assertions {

    public static CategoryAssert assertThat(Category actual) {
        return new CategoryAssert(actual);
    }

    public static JakartaValidationAssert assertThat(Set<ConstraintViolation<Object>> actual) {
        return new JakartaValidationAssert(actual);
    }
}
