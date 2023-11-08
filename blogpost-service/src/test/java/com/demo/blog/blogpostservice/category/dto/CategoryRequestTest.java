package com.demo.blog.blogpostservice.category.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.Set;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryConstants.BLANK_MSG;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryConstants.TOO_LONG_MSG;

@TestMethodOrder(MethodOrderer.Random.class)
public class CategoryRequestTest {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @ParameterizedTest
    @MethodSource("com.demo.blog.blogpostservice.datasupply.StringDataSupply#blankStrings")
    @NullSource
    void shouldThrowExceptionOnBlankCategory(String blankName) {
        // arrange
        CategoryRequest request = new CategoryRequest(blankName);

        // act & assert
        assertThat(validate(request)).containsOnlyExceptionMessages(BLANK_MSG);
    }

    @ParameterizedTest
    @MethodSource("com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply#tooLongCategoryNames")
    void shouldThrowExceptionOnCategoryNameLongerThan32Chars(String tooLongName) {
        // arrange
        CategoryRequest request = new CategoryRequest(tooLongName);

        // act & assert
        assertThat(validate(request)).containsOnlyExceptionMessages(TOO_LONG_MSG);
    }

    @ParameterizedTest
    @MethodSource("com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply#justRightLengthCategoryNames")
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
