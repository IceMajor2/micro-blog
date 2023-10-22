package com.demo.blog.postservice.category;

import com.demo.blog.postservice.category.dto.CategoryRequest;
import com.demo.blog.postservice.category.dto.CategoryResponse;
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

import static com.demo.blog.postservice.assertions.AllAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@TestClassOrder(ClassOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService SUT;
    @Mock
    private CategoryRepository repository;

    private static final Long ANY_LONG = 1L;
    private static final String ANY_STRING = "ANY_STRING";

    @Nested
    class GetReq {

        @ParameterizedTest
        @MethodSource("com.demo.blog.postservice.category.CategoryServiceTest#categoryNames")
        void shouldReturnCategoryOnGetWithParameter(String categoryName) {
            // arrange
            String expectedName = new String(categoryName);
            Category category = new CategoryBuilder()
                    .withId(ANY_LONG)
                    .withName(categoryName)
                    .build();
            when(repository.findByName(categoryName)).thenReturn(Optional.of(category));

            // act
            CategoryResponse actual = SUT.getByName(categoryName);

            // assert
            assertThat(actual).isNamed(expectedName);
            verify(repository, times(1)).findByName(categoryName);
        }

        @Test
        void shouldThrowExceptionOnCategoryNotFound() {
            assertThatExceptionOfType(CategoryNotFoundException.class)
                    .isThrownBy(() -> SUT.getByName("NON_EXISTING_CATEGORY"));
        }

        @Test
        void shouldThrowExceptionOnNullCategoryName() {
            assertThatExceptionOfType(NullPointerException.class)
                    .isThrownBy(() -> SUT.getByName(null));
        }

        @Test
        void shouldReturnEmptyListWhenCollectionIsEmpty() {
            // arrange
            when(repository.findAll()).thenReturn(Collections.emptyList());

            // act
            List<CategoryResponse> actual = SUT.getAll();

            // assert
            assertThat(actual).isEmpty();
            verify(repository, times(1)).findAll();
        }

        @Test
        void shouldReturnListOfAllCategories() {
            // arrange
            List<Category> stubbedCategories = categoryNames()
                    .map(name -> new CategoryBuilder()
                            .withId(ANY_LONG)
                            .withName(name)
                            .build())
                    .toList();
            List<CategoryResponse> expectedCategories = categoryNames()
                    .map(name -> CategoryResponse.builder()
                            .name(name)
                            .build())
                    .toList();
            when(repository.findAll()).thenReturn(stubbedCategories);

            // act
            List<CategoryResponse> actual = SUT.getAll();

            // assert
            assertThat(actual).containsExactlyElementsOf(expectedCategories);
        }

        @Test
        void shouldReturnCategoryOnGetWithPathVariable() {
            // arange
            String expectedName = new String(ANY_STRING);
            Category category = new CategoryBuilder()
                    .withId(ANY_LONG)
                    .withName(ANY_STRING)
                    .build();
            when(repository.findById(ANY_LONG)).thenReturn(Optional.of(category));

            // act
            CategoryResponse actual = SUT.getById(ANY_LONG);

            // assert
            assertThat(actual).isNamed(expectedName);
        }

        @Test
        void shouldThrowExceptionOnNullId() {
            assertThatExceptionOfType(NullPointerException.class)
                    .isThrownBy(() -> SUT.getById(null));
        }
    }

    @Nested
    class PostReq {

        @ParameterizedTest
        @MethodSource("com.demo.blog.postservice.category.CategoryServiceTest#validRequests")
        void shouldAcceptValidCategory(CategoryRequest request) {
            // arrange
            String expectedName = new String(request.name());
            Category stub = new CategoryBuilder()
                    .withId(ANY_LONG)
                    .withName(expectedName)
                    .build();
            Category requestAsModel = new CategoryBuilder()
                    .fromRequest(request)
                    .build();
            when(repository.save(requestAsModel)).thenReturn(stub);

            // act
            CategoryResponse actual = SUT.add(request);

            // assert
            assertThat(actual).isNamed(expectedName);
            verify(repository, times(1)).save(requestAsModel);
        }

        @Test
        void shouldThrowExceptionOnConflict() {
            // arrange
            CategoryRequest request = CategoryRequest.builder().name("Java").build();
            when(repository.existsByName(request.name())).thenReturn(true);

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
