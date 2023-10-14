package com.demo.blog.postservice.service;

import com.demo.blog.postservice.domain.Category;
import com.demo.blog.postservice.exception.CategoryNotFoundException;
import com.demo.blog.postservice.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@TestClassOrder(ClassOrderer.Random.class)
public class CategoryServiceTest {

    private CategoryService SUT;
    private CategoryRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(CategoryRepository.class);
        SUT = new CategoryService(repository);
    }

    @Test
    void shouldThrowExceptionOnCategoryNotFound() {
        assertThatExceptionOfType(CategoryNotFoundException.class)
                .isThrownBy(() -> SUT.get("SOME CATEGORY"));
    }

    @ParameterizedTest
    @MethodSource("validCategoryNames")
    void shouldReturnCategory(String categoryName) {
        // arrange
        Category expected = Category.builder()
                .id(1L)
                .name(categoryName)
                .posts(Collections.emptyList())
                .build();
        when(repository.findByName(categoryName)).thenReturn(Optional.of(expected));

        // act
        Category actual = SUT.get(categoryName);

        // assert
        assertThat(actual).isEqualTo(expected);
        verify(repository, times(1)).findByName(categoryName);
        verifyNoMoreInteractions(repository);
    }

    private static Stream<String> validCategoryNames() {
        return Stream.of(
                "Java",
                "Threads",
                "Security",
                "Microservices",
                "Project Management"
        );
    }
}
