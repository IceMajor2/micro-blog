package com.demo.blog.blogpostservice.author.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.blogpostservice.author.datasupply.AuthorConstants.*;
import static com.demo.blog.blogpostservice.datasupply.Constants.ANY_EMAIL;

@TestMethodOrder(MethodOrderer.Random.class)
class AuthorRequestTest {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @ParameterizedTest
    @MethodSource("com.demo.blog.blogpostservice.datasupply.StringDataSupply#blankStrings")
    @NullSource
    void shouldThrowExceptionOnUsernameBlank(String blank) {
        // act
        AuthorRequest actual = new AuthorRequest(ANY_EMAIL, blank);

        // assert
        assertThat(validator.validate(actual)).containsExceptionMessages(USERNAME_BLANK_MSG);
    }

    @ParameterizedTest
    @MethodSource("com.demo.blog.blogpostservice.post.datasupply.PostDataSupply#lessThanFiveCharactersTitles")
    void shouldThrowExceptionOnUsernameShorterThan5Chars(String tooShort) {
        // act
        AuthorRequest actual = new AuthorRequest(ANY_EMAIL, tooShort);

        // assert
        assertThat(validator.validate(actual)).containsOnlyExceptionMessages(USERNAME_TOO_SHORT_MSG);
    }

    @ParameterizedTest
    @MethodSource("com.demo.blog.blogpostservice.post.datasupply.PostDataSupply#moreThan255CharactersTitles")
    void shouldThrowExceptionOnUsernameLongerThan255Chars(String tooLong) {
        // act
        AuthorRequest actual = new AuthorRequest(ANY_EMAIL, tooLong);

        // assert
        assertThat(validator.validate(actual)).containsOnlyExceptionMessages(USERNAME_TOO_LONG_MSG);
    }

    @ParameterizedTest
    @MethodSource("com.demo.blog.blogpostservice.post.datasupply.PostDataSupply#justMoreOrEqualThanFiveCharactersTitles")
    void shouldAcceptUsernamesJustMoreOrEqualThanFiveCharactersLong(String moreOrEqualThanFiveChars) {
        // act
        AuthorRequest actual = new AuthorRequest(ANY_EMAIL, moreOrEqualThanFiveChars);

        // assert
        assertThat(validator.validate(actual)).isValid();
    }
}