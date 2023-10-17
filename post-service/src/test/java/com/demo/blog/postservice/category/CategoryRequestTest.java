package com.demo.blog.postservice.category;

import com.demo.blog.postservice.category.dto.CategoryRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static com.demo.blog.postservice.assertions.JakartaValidationAssert.assertThat;

public class CategoryRequestTest {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private static final String NOT_BLANK_MSG = "Name must be specified";

    @ParameterizedTest
    @ValueSource(strings = {"", "  \t  \n\t", "      "})
    @NullSource
    void shouldThrowExceptionOnBlankCategory(String categoryName) {
        CategoryRequest request = CategoryRequest.builder().name(categoryName).build();
        assertThat(validate(request)).containsOnlyExceptionMessages(NOT_BLANK_MSG);
    }

    private static Set<ConstraintViolation<Object>> validate(Object object) {
        return validator.validate(object);
    }
}
