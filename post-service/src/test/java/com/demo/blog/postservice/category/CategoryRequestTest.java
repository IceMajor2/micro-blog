package com.demo.blog.postservice.category;

import com.demo.blog.postservice.category.dto.CategoryRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static com.demo.blog.postservice.assertions.AllAssertions.assertThat;

@TestMethodOrder(MethodOrderer.Random.class)
public class CategoryRequestTest {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private static final String EXP_NOT_BLANK_MSG = "Name must be specified";
    private static final String EXP_NAME_TOO_LONG_MSG = "Category name cannot exceed 32 characters";

    @ParameterizedTest
    @ValueSource(strings = {"", "  \t  \n\t", "      "})
    @NullSource
    void shouldThrowExceptionOnBlankCategory(String categoryName) {
        CategoryRequest request = new CategoryRequest(categoryName);
        assertThat(validate(request)).containsOnlyExceptionMessages(EXP_NOT_BLANK_MSG);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "THIRTY_THREE_CHARS_STRING_REQUEST",
            "_THIRTY_MORE_CHARS_STRING_REQUEST_",
            "_THIRTY_MORE_CHARS_STRING_REQUEST__"
    })
    void shouldThrowExceptionOnCategoryNameLongerThan32Chars(String tooLongName) {
        CategoryRequest request = new CategoryRequest(tooLongName);
        assertThat(validate(request)).containsOnlyExceptionMessages(EXP_NAME_TOO_LONG_MSG);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "32_CHARS_STRING_REQUEST_ACCEPTED",
            "31_CHAR_STRING_REQUEST_ACCEPTED"
    })
    void shouldAcceptRequestsWithNamesEqualOrShorterThan32Chars(String rightSizeName) {
        CategoryRequest request = new CategoryRequest(rightSizeName);
        assertThat(validate(request)).isValid();
    }

    private static Set<ConstraintViolation<Object>> validate(Object object) {
        return validator.validate(object);
    }
}
