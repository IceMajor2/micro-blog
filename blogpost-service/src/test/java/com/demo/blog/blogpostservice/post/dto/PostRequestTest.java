package com.demo.blog.blogpostservice.post.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.blogpostservice.datasupply.Constants.ANY_STRING;
import static com.demo.blog.blogpostservice.post.datasupply.PostConstants.BLANK_MSG;

@TestMethodOrder(MethodOrderer.Random.class)
class PostRequestTest {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @ParameterizedTest
    @MethodSource("com.demo.blog.blogpostservice.datasupply.StringDataSupply#blankStrings")
    @NullSource
    void shouldThrowExceptionOnNameBlank(String blankName) {
        // act
        PostRequest actual = new PostRequest(blankName, ANY_STRING);

        // assert
        assertThat(validator.validate(actual)).containsExceptionMessages(BLANK_MSG);
    }
}