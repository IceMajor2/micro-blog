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

import java.util.*;
import java.util.stream.Collectors;

import static com.demo.blog.postservice.assertions.AllAssertions.assertThat;
import static com.demo.blog.postservice.assertions.AllAssertions.assertThatExceptionOfType;
import static com.demo.blog.postservice.category.CategoryDataSupply.sortedCategories;
import static org.mockito.Mockito.*;

@TestClassOrder(ClassOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryServiceImpl SUT;
    @Mock
    private CategoryRepository repository;

    static final Long ANY_LONG = 1L;
    static final Long NEGATIVE_LONG = -1L;
    static final String ANY_STRING = "ANY_STRING";

    @Nested
    class GetReq {

        @ParameterizedTest
        @MethodSource("com.demo.blog.postservice.category.CategoryDataSupply#categories")
        void shouldReturnCategoryOnGetWithParameter(Category category) {
            // arrange
            String expectedName = new String(category.getName());
            when(repository.findByName(category.getName())).thenReturn(Optional.of(category));

            // act
            CategoryResponse actual = SUT.getByName(category.getName());

            // assert
            assertThat(actual).isNamed(expectedName);
        }

        @Test
        void shouldThrowExceptionOnCategoryNotFound() {
            String noSuchCategory = "NON_EXISTING_CATEGORY";
            assertThatExceptionOfType(CategoryNotFoundException.class)
                    .isThrownBy(() -> SUT.getByName(noSuchCategory))
                    .withMessage(STR. "Category '\{ noSuchCategory }' was not found" );
        }

        @Test
        void shouldThrowExceptionOnNullCategoryName() {
            assertThatExceptionOfType(NullPointerException.class)
                    .isThrownBy(() -> SUT.getByName(null))
                    .withMessage("Category name was null");
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
                    .isThrownBy(() -> SUT.getById(null))
                    .withMessage("Category ID was null");
        }

        @Test
        void shouldThrowExceptionOnIdNotFound() {
            assertThatExceptionOfType(CategoryNotFoundException.class)
                    .isThrownBy(() -> SUT.getById(NEGATIVE_LONG))
                    .withMessage(STR. "Category of ''\{ NEGATIVE_LONG }'' ID was not found" );
        }

        @Test
        void shouldReturnEmptyListWhenCollectionIsEmpty() {
            // arrange
            when(repository.findByOrderByNameAsc()).thenReturn(Collections.emptySet());

            // act
            Set<CategoryResponse> actual = SUT.getAll();

            // assert
            assertThat(actual).isEmpty();
        }

        @Test
        void shouldReturnListOfAllCategoriesSortedInAlphabeticOrder() {
            // arrange
            Set<Category> stubbedCategories = CategoryDataSupply.toSet(sortedCategories());
            Set<CategoryResponse> expectedCategories = CategoryDataSupply.categories()
                    .map(CategoryResponse::new)
                    .collect(Collectors.toSet());
            when(repository.findByOrderByNameAsc()).thenReturn(stubbedCategories);

            // act
            Set<CategoryResponse> actual = SUT.getAll();

            // assert
            assertThat(toList(actual))
                    .isSortedAccordingTo(Comparator.comparing(CategoryResponse::name))
                    .containsAll(expectedCategories);
        }
    }

    @Nested
    class PostReq {

        @ParameterizedTest
        @MethodSource("com.demo.blog.postservice.category.CategoryDataSupply#validRequests")
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
            assertThatExceptionOfType(CategoryAlreadyExistsException.class)
                    .isThrownBy(() -> SUT.add(request))
                    .withMessage(STR. "Category '\{ request.name() }' already exists" );
        }
    }

    private <T> List<T> toList(Set<T> set) {
        return set.stream().collect(Collectors.toList());
    }
}
