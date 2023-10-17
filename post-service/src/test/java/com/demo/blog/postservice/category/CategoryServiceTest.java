package com.demo.blog.postservice.category;

import com.demo.blog.postservice.assertions.PostAssertions;
import com.demo.blog.postservice.category.dto.CategoryRequest;
import com.demo.blog.postservice.category.exception.CategoryAlreadyExistsException;
import com.demo.blog.postservice.category.exception.CategoryNotFoundException;
import com.demo.blog.postservice.post.Post;
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
import java.util.Set;
import java.util.stream.Stream;

import static com.demo.blog.postservice.assertions.PostAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@TestClassOrder(ClassOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService SUT;

    @Mock
    private CategoryRepository repository;

    private Long ANY_LONG = 1L;
    private Set<Post> ANY_SET = Collections.emptySet();

    @Nested
    class GetReq {

        @ParameterizedTest
        @MethodSource("com.demo.blog.postservice.category.CategoryServiceTest#categoryNames")
        void shouldReturnCategory(String categoryName) {
            // arrange
            String expectedName = new String(categoryName);
            Category category = new CategoryBuilder()
                    .withId(ANY_LONG)
                    .withName(categoryName)
                    .build();
            when(repository.findByName(categoryName)).thenReturn(Optional.of(category));

            // act
            Category actual = SUT.get(categoryName);

            // assert
            PostAssertions.assertThat(actual).isNamed(expectedName);
            verify(repository, times(1)).findByName(categoryName);
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
        }

        @Test
        void shouldReturnListOfAllCategories() {
            // arange
            List<Category> expected = categoryNames()
                    .map(name -> new CategoryBuilder()
                            .withId(ANY_LONG)
                            .withName(name)
                            .build())
                    .toList();
            when(repository.findAll()).thenReturn(expected);

            // act
            List<Category> actual = SUT.getAll();

            // assert
            assertThat(actual).containsExactlyElementsOf(expected);
        }
    }

    @Nested
    class PostReq {

        @ParameterizedTest
        @MethodSource("com.demo.blog.postservice.category.CategoryServiceTest#validRequests")
        void shouldAcceptValidCategory(CategoryRequest request) {
            // arrange
            String expectedName = new String(request.getName());
            Category expected = new CategoryBuilder()
                    .withId(1L)
                    .withName(request.getName())
                    .build();
            Category requestAsModel = new CategoryBuilder()
                    .fromRequest(request)
                    .build();
            when(repository.save(requestAsModel)).thenReturn(expected);

            // act
            Category actual = SUT.add(request);

            // assert
            PostAssertions.assertThat(actual).isNamed(expectedName);
            verify(repository, times(1)).save(requestAsModel);
        }

        @Test
        void shouldThrowExceptionOnConflict() {
            // arrange
            CategoryRequest request = CategoryRequest.builder().name("Java").build();
            when(repository.existsByName(request.getName())).thenReturn(true);

            // act & arrange
            assertThatExceptionOfType(CategoryAlreadyExistsException.class).isThrownBy(() -> SUT.add(request));
        }
    }

    private static Stream<String> categoryNames() {
        return Stream.of(
                "Java",
                "Threads",
                "Security",
                "Microservices",
                "Project Management"
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
