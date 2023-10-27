package com.demo.blog.postservice.category;

import com.demo.blog.postservice.category.dto.CategoryRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static com.demo.blog.postservice.assertion.AllAssertions.assertThat;

@TestMethodOrder(MethodOrderer.Random.class)
public class CategoryRequestTest {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    public static final String NOT_BLANK_MSG = "Category name must be specified";
    public static final String NAME_TOO_LONG_MSG = "Category name cannot exceed 32 characters";

    @ParameterizedTest
    @MethodSource("com.demo.blog.postservice.datasupply.StringDataSupply#blankStrings")
    @NullSource
    void shouldThrowExceptionOnBlankCategory(String categoryName) {
        // arrange
        CategoryRequest request = new CategoryRequest(categoryName);

        // act & assert
        assertThat(validate(request)).containsOnlyExceptionMessages(NOT_BLANK_MSG);
    }

    @ParameterizedTest
    @MethodSource("com.demo.blog.postservice.category.datasupply.CategoryDataSupply#tooLongRequestNames")
    void shouldThrowExceptionOnCategoryNameLongerThan32Chars(String tooLongName) {
        // arrange
        CategoryRequest request = new CategoryRequest(tooLongName);

        // act & assert
        assertThat(validate(request)).containsOnlyExceptionMessages(NAME_TOO_LONG_MSG);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "32_CHARS_STRING_REQUEST_ACCEPTED",
            "31_CHAR_STRING_REQUEST_ACCEPTED"
    })
    void shouldAcceptRequestsWithNamesEqualOrShorterThan32Chars(String rightSizeName) {
        // arrange
        CategoryRequest request = new CategoryRequest(rightSizeName);

        // act & assert
        assertThat(validate(request)).isValid();
    }

    private static Set<ConstraintViolation<Object>> validate(Object object) {
        return validator.validate(object);
    }
}
