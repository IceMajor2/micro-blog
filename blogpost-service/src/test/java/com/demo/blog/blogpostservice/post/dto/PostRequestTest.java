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
class PostRequestTest {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @ParameterizedTest
    @MethodSource("com.demo.blog.blogpostservice.datasupply.StringDataSupply#blankStrings")
    @NullSource
    void shouldThrowExceptionOnNameBlank(String blank) {
        // act
        PostRequest actual = new PostRequest(blank, ANY_STRING);

        // assert
        assertThat(validator.validate(actual)).containsExceptionMessages(TITLE_BLANK_MSG);
    }

    @ParameterizedTest
    @MethodSource("com.demo.blog.blogpostservice.post.datasupply.PostDataSupply#lessThanFiveCharactersTitles")
    void shouldThrowExceptionOnTitleShorterThan5Chars(String tooShort) {
        // act
        PostRequest actual = new PostRequest(tooShort, ANY_STRING);

        // assert
        assertThat(validator.validate(actual)).containsOnlyExceptionMessages(TOO_SHORT_MSG);
    }

    @ParameterizedTest
    @MethodSource("com.demo.blog.blogpostservice.post.datasupply.PostDataSupply#moreThan255CharactersTitles")
    void shouldThrowExceptionOnTitleLongerThan255Chars(String tooLong) {
        // act
        PostRequest actual = new PostRequest(tooLong, ANY_STRING);

        // assert
        assertThat(validator.validate(actual)).containsOnlyExceptionMessages(TOO_LONG_MSG);
    }

    @ParameterizedTest
    @MethodSource("com.demo.blog.blogpostservice.post.datasupply.PostDataSupply#justMoreOrEqualThanFiveCharactersTitles")
    void shouldAcceptTitlesJustMoreOrEqualThanFiveCharactersLong(String moreOrEqualThanFiveChars) {
        // act
        PostRequest actual = new PostRequest(moreOrEqualThanFiveChars, ANY_STRING);

        // assert
        assertThat(validator.validate(actual)).isValid();
    }

    @ParameterizedTest
    @MethodSource("com.demo.blog.blogpostservice.post.datasupply.PostDataSupply#justLessOrEqualThan255CharactersTitles")
    void shouldAcceptTitlesJustLessOrEqualThan255CharactersLong(String lessOrEqualThan255Chars) {
        // act
        PostRequest actual = new PostRequest(lessOrEqualThan255Chars, ANY_STRING);

        // assert
        assertThat(validator.validate(actual)).isValid();
    }

    @ParameterizedTest
    @MethodSource("com.demo.blog.blogpostservice.datasupply.StringDataSupply#blankStrings")
    @NullSource
    void shouldThrowExceptionOnBodyBlank(String blank) {
        // act
        PostRequest actual = new PostRequest(ANY_STRING, blank);

        // assert
        assertThat(validator.validate(actual)).containsOnlyExceptionMessages(BODY_BLANK_MSG);
    }

    @Test
    void shouldAcceptAnyBodyContainingNonWhitespaceCharacters() {
        // act
        PostRequest actual = new PostRequest(ANY_STRING, ANY_STRING);

        // assert
        assertThat(validator.validate(actual)).isValid();
    }
}