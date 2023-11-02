package com.demo.blog.blogpostservice.category.dto;

import com.demo.blog.blogpostservice.category.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Objects;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;

class CategoryResponseTest {

    @ParameterizedTest
    @MethodSource("com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply#validCategories")
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
        // arrange
        CategoryResponse response1 = new CategoryResponse(1L, "Java");
        CategoryResponse response2 = new CategoryResponse(1L, "Java");

        // act & assert
        assertThat(Objects.equals(response1, response2)).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "1,Threads,2,Threads",
            "10,C,10,C++",
            "5,Learning,3,Discipline"
    })
    void equalsShouldReturnFalseOnDifferingObjects(Long id1, String name1, Long id2, String name2) {
        // arrange
        CategoryResponse response1 = new CategoryResponse(id1, name1);
        CategoryResponse response2 = new CategoryResponse(id2, name2);

        // act & assert
        assertThat(Objects.equals(response1, response2)).isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "null,Programming,1,Programming",
            "5,Microservices,null,Microservices"
    }, nullValues = "null")
    void equalsShouldBeFalseIfOneEntityDoesNotHaveId(Long id1, String name1, Long id2, String name2) {
        // arrange
        CategoryResponse response1 = new CategoryResponse(id1, name1);
        CategoryResponse response2 = new CategoryResponse(id2, name2);

        // act & assert
        assertThat(Objects.equals(response1, response2)).isFalse();
    }

    @Test
    void equalsShouldReturnTrueIfIdMissingButOtherwiseSame() {
        // arrange
        CategoryResponse response1 = new CategoryResponse(null, "Python");
        CategoryResponse response2 = new CategoryResponse(null, "Python");

        // act & assert
        assertThat(Objects.equals(response1, response2)).isTrue();
    }

    @Test
    void hashCodeShouldBeSameForEqualObjects() {
        // arrange
        CategoryResponse response1 = new CategoryResponse(1L, "C#");
        CategoryResponse response2 = new CategoryResponse(1L, "C#");

        // act
        int hash1 = response1.hashCode();
        int hash2 = response2.hashCode();

        // assert
        assertThat(hash1).isEqualTo(hash2);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,Scala,2,Kotlin",
            "1,Concurrency,1,Threads",
            "1,Management,2,Management",
            "2,Data,null,Data"
    }, nullValues = "null")
    void hashCodeShouldBeDifferentForDifferingObjects(Long id1, String name1, Long id2, String name2) {
        // arrange
        CategoryResponse response1 = new CategoryResponse(id1, name1);
        CategoryResponse response2 = new CategoryResponse(id2, name2);

        // act
        int hash1 = response1.hashCode();
        int hash2 = response2.hashCode();

        // assert
        assertThat(hash1).isNotEqualTo(hash2);
    }

    @Test
    void hashCodeShouldStaySameOnIdMissingButOtherwiseEqual() {
        // arrange
        CategoryResponse response1 = new CategoryResponse(null, "C#");
        CategoryResponse response2 = new CategoryResponse(null, "C#");

        // act
        int hash1 = response1.hashCode();
        int hash2 = response2.hashCode();

        // assert
        assertThat(hash1).isEqualTo(hash2);
    }
}