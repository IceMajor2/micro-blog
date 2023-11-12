package com.demo.blog.blogpostservice.postcategory.dto;

import com.demo.blog.blogpostservice.post.datasupply.PostConstants;
import com.demo.blog.blogpostservice.postcategory.datasupply.PostCategoryConstants;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.CONTAINERS_CATEGORY;
import static com.demo.blog.blogpostservice.datasupply.Constants.ANY_LONG;

@TestMethodOrder(MethodOrderer.Random.class)
class PostCategoryRequestTest {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void shouldThrowExceptionOnIdNull() {
        // act
        PostCategoryRequest actual = new PostCategoryRequest(null, List.of(CONTAINERS_CATEGORY.getId()));

        // assert
        assertThat(validator.validate(actual)).containsExceptionMessages(PostConstants.NULL_ID_REQUEST_MSG);
    }

    @Test
    void shouldThrowExceptionOnCategoryIdsNull() {
        // act
        PostCategoryRequest actual = new PostCategoryRequest(ANY_LONG, null);

        // assert
        assertThat(validator.validate(actual)).containsExceptionMessages(PostCategoryConstants.CATEGORY_IDS_BLANK);
    }

    @Test
    void shouldThrowExceptionOnCategoryIdListBlank() {
        // act
        PostCategoryRequest actual = new PostCategoryRequest(ANY_LONG, List.of());

        // assert
        assertThat(validator.validate(actual)).containsExceptionMessages(PostCategoryConstants.CATEGORY_IDS_BLANK);
    }

    @Test
    void shouldAcceptRequest() {
        // act
        PostCategoryRequest actual = new PostCategoryRequest(ANY_LONG, List.of(CONTAINERS_CATEGORY.getId()));

        // assert
        assertThat(validator.validate(actual)).isValid();
    }
}