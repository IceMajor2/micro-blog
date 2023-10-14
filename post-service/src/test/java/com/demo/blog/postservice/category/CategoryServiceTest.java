package com.demo.blog.postservice.category;

import com.demo.blog.postservice.category.dto.CategoryRequest;
import com.demo.blog.postservice.category.exception.CategoryAlreadyExistsException;
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
            verify(repository, times(1)).findAll();
            verifyNoMoreInteractions(repository);
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

    @Nested
    class Post {

        @ParameterizedTest
        @MethodSource("com.demo.blog.postservice.category.CategoryServiceTest#validRequests")
        void shouldAcceptValidCategory(CategoryRequest request) {
            // arrange
            Category expected = Category.builder()
                    .id(1L)
                    .name(request.getName())
                    .posts(Collections.emptySet())
                    .build();
            Category requestAsModel = request.toModel();
            when(repository.save(requestAsModel)).thenReturn(expected);

            // act
            Category actual = SUT.add(request);

            // assert
            assertThat(actual).isEqualTo(expected);
            verify(repository, times(1)).save(requestAsModel);
        }

        @Test
        void shouldThrowExceptionOnNullCategory() {
            CategoryRequest request = CategoryRequest.builder().name(null).build();
            assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> SUT.add(request));
        }

        @Test
        void shouldThrowExceptionOnConflict() {
            // arrange
            CategoryRequest request = CategoryRequest.builder().name("Java").build();
            Category category = Category.builder()
                    .id(9L)
                    .name("Java")
                    .posts(Collections.emptySet())
                    .build();
            when(repository.existsByName(request.getName())).thenReturn(true);

            // act & arrange
            assertThatExceptionOfType(CategoryAlreadyExistsException.class).isThrownBy(() -> SUT.add(request));
        }
    }

    private static Stream<Category> categoriesWithEmptyPosts() {
        return Stream.of(
                Category.builder().id(1L).name("Java").posts(Collections.emptySet()).build(),
                Category.builder().id(2L).name("Threads").posts(Collections.emptySet()).build(),
                Category.builder().id(3L).name("Security").posts(Collections.emptySet()).build(),
                Category.builder().id(4L).name("Microservices").posts(Collections.emptySet()).build(),
                Category.builder().id(5L).name("Project Management").posts(Collections.emptySet()).build()
        );
    }

    private static Stream<CategoryRequest> validRequests() {
        return Stream.of(
                CategoryRequest.builder().name("Python").build(),
                CategoryRequest.builder().name("Backend").build(),
                CategoryRequest.builder().name("Algorithms").build(),
                CategoryRequest.builder().name("Data Structures").build()
        );
    }
}
