package com.demo.blog.postservice.service;

import com.demo.blog.postservice.domain.Category;
import com.demo.blog.postservice.exception.CategoryNotFoundException;
import com.demo.blog.postservice.repository.CategoryRepository;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@TestClassOrder(ClassOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService SUT;

    @Mock
    private CategoryRepository repository;

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

    @Test
    void shouldThrowExceptionOnCategoryNotFound() {
        assertThatExceptionOfType(CategoryNotFoundException.class)
                .isThrownBy(() -> SUT.get("SOME CATEGORY"));
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

    private static Stream<String> invalidCategoryNames() {
        return Stream.of(
                "",
                null,
                "   \n \t"
        );
    }
}
