package com.demo.blog.blogpostservice.post.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.blogpostservice.datasupply.Constants.ANY_STRING;
import static com.demo.blog.blogpostservice.post.datasupply.PostConstants.*;

@TestMethodOrder(MethodOrderer.Random.class)
class PostChangeTitleRequestTest {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @ParameterizedTest
    @MethodSource("com.demo.blog.blogpostservice.datasupply.StringDataSupply#blankStrings")
    @NullSource
    void shouldThrowExceptionOnTitleBlank(String blankString) {
        // act
        PostChangeTitleRequest actual = new PostChangeTitleRequest(blankString);

        // assert
        assertThat(validator.validate(actual)).containsExceptionMessages(TITLE_BLANK_MSG);
    }

    @Test
    void shouldAcceptRequest() {
        // act
        PostChangeTitleRequest actual = new PostChangeTitleRequest(ANY_STRING);

        // assert
        assertThat(validator.validate(actual)).isValid();
    }

    @ParameterizedTest
    @MethodSource("com.demo.blog.blogpostservice.post.datasupply.PostDataSupply#lessThanFiveCharactersTitles")
    void shouldThrowExceptionOnTitleShorterThan5Chars(String tooShort) {
        // act
        PostChangeTitleRequest actual = new PostChangeTitleRequest(tooShort);

        // assert
        assertThat(validator.validate(actual)).containsOnlyExceptionMessages(TOO_SHORT_MSG);
    }

    @ParameterizedTest
    @MethodSource("com.demo.blog.blogpostservice.post.datasupply.PostDataSupply#moreThan255CharactersTitles")
    void shouldThrowExceptionOnTitleLongerThan255Chars(String tooLong) {
        // act
        PostChangeTitleRequest actual = new PostChangeTitleRequest(tooLong);

        // assert
        assertThat(validator.validate(actual)).containsOnlyExceptionMessages(TOO_LONG_MSG);
    }

    @ParameterizedTest
    @MethodSource("com.demo.blog.blogpostservice.post.datasupply.PostDataSupply#justMoreOrEqualThanFiveCharactersTitles")
    void shouldAcceptTitlesJustMoreOrEqualThanFiveCharactersLong(String moreOrEqualThanFiveChars) {
        // act
        PostChangeTitleRequest actual = new PostChangeTitleRequest(moreOrEqualThanFiveChars);

        // assert
        assertThat(validator.validate(actual)).isValid();
    }

    @ParameterizedTest
    @MethodSource("com.demo.blog.blogpostservice.post.datasupply.PostDataSupply#justLessOrEqualThan255CharactersTitles")
    void shouldAcceptTitlesJustLessOrEqualThan255CharactersLong(String lessOrEqualThan255Chars) {
        // act
        PostChangeTitleRequest actual = new PostChangeTitleRequest(lessOrEqualThan255Chars);

        // assert
        assertThat(validator.validate(actual)).isValid();
    }
}