package com.demo.blog.postservice.category;

import com.demo.blog.postservice.category.exception.CategoryNotFoundException;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@TestClassOrder(ClassOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService SUT;

    @Mock
    private CategoryRepository repository;

    @Nested
    class Get {

        @ParameterizedTest
        @MethodSource("com.demo.blog.postservice.category.CategoryServiceTest#categoriesWithEmptyPosts")
        void shouldReturnCategory(Category category) {
            // arrange
            when(repository.findByName(category.getName())).thenReturn(Optional.of(category));

            // act
            Category actual = SUT.get(category.getName());

            // assert
            assertThat(actual).isEqualTo(category);
            verify(repository, times(1)).findByName(category.getName());
            verifyNoMoreInteractions(repository);
        }

        @Test
        void shouldThrowExceptionOnCategoryNotFound() {
            assertThatExceptionOfType(CategoryNotFoundException.class)
                    .isThrownBy(() -> SUT.get("SOME CATEGORY"));
        }

        @Test
        void shouldThrowExceptionOnNullCategoryName() {
            assertThatExceptionOfType(NullPointerException.class)
                    .isThrownBy(() -> SUT.get(null));
        }

        @Test
        void shouldReturnEmptyListWhenCollectionIsEmpty() {
            // arrange
            when(repository.findAll()).thenReturn(Collections.emptyList());

            // act
            List<Category> actual = SUT.getAll();

            // assert
            assertThat(actual).isEmpty();
        }

        @Test
        void shouldReturnListOfAllCategories() {
            // arange
            List<Category> expected = categoriesWithEmptyPosts().toList();
            when(repository.findAll()).thenReturn(expected);

            // act
            List<Category> actual = SUT.getAll();

            // assert
            assertThat(actual).containsExactlyElementsOf(expected);
        }
    }

    private static Stream<Category> categoriesWithEmptyPosts() {
        return Stream.of(
                Category.builder().id(1L).name("Java").posts(Collections.emptyList()).build(),
                Category.builder().id(2L).name("Threads").posts(Collections.emptyList()).build(),
                Category.builder().id(3L).name("Security").posts(Collections.emptyList()).build(),
                Category.builder().id(4L).name("Microservices").posts(Collections.emptyList()).build(),
                Category.builder().id(5L).name("Project Management").posts(Collections.emptyList()).build()
        );
    }
}
