package com.demo.blog.postservice.category;

import com.demo.blog.postservice.category.dto.CategoryResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static com.demo.blog.postservice.assertion.AllAssertions.assertThat;

class CategoryResponseTest {

    @ParameterizedTest
    @MethodSource("com.demo.blog.postservice.category.datasupply.CategoryDataSupply#categories")
    void constructorWithCategoryParamTest(Category category) {
        // arrange
        CategoryResponse expected = new CategoryResponse(category.getId(), category.getName());

        // act
        CategoryResponse actual = new CategoryResponse(category);

        // assert
        assertThat(actual)
                .hasId(expected.id())
                .isNamed(expected.name());
    }

    @Test
    void equalsShouldReturnTrueOnEqualObjects() {
    }

    @Test
    void equalsShouldReturnFalseOnDifferingObjects() {
    }

    @Test
    void equalsShouldNotBreakOnIdMissing() {
    }

    @Test
    void hashCodeShouldBeSameForEqualObjects() {
    }

    @Test
    void hashCodeShouldBeDifferentForDifferingObjects() {
    }

    @Test
    void hashCodeShouldNotBreakOnIdMissing() {
    }
}