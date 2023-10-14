package com.demo.blog.postservice.category;

import com.demo.blog.postservice.category.dto.CategoryRequest;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryRequestTest {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private static final String NOT_BLANK_MSG = "Name must be specified";

    @ParameterizedTest
    @ValueSource(strings = {"", "  \t  \n\t", "      "})
    @NullSource
    void shouldThrowExceptionOnBlankCategory(String categoryName) {
        CategoryRequest request = CategoryRequest.builder().name("").build();
        // TODO: change into custom matcher
        assertThat(errorMessageExists(request, NOT_BLANK_MSG))
                .withFailMessage("Error message was not found")
                .isTrue();
    }

    private static boolean errorMessageExists(Object object, String errorMessage) {
        return validator.validate(object).stream()
                .anyMatch(violation -> Objects.equals(errorMessage, violation.getMessage()));
    }
}
