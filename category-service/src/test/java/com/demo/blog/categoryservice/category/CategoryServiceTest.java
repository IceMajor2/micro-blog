package com.demo.blog.categoryservice.category;

import com.demo.blog.categoryservice.category.dto.CategoryRequest;
import com.demo.blog.categoryservice.category.dto.CategoryResponse;
import com.demo.blog.categoryservice.category.exception.CategoryAlreadyExistsException;
import com.demo.blog.categoryservice.category.exception.CategoryNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static com.demo.blog.categoryservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.categoryservice.assertion.AllAssertions.assertThatExceptionOfType;
import static com.demo.blog.categoryservice.category.datasupply.CategoryDataSupply.*;
import static com.demo.blog.categoryservice.category.datasupply.Constants.*;
import static org.mockito.Mockito.*;

@TestClassOrder(ClassOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryServiceImpl SUT;
    @Mock
    private CategoryRepository repository;

    @Nested
    @TestMethodOrder(MethodOrderer.Random.class)
    class GetRequests {

        @ParameterizedTest
        @MethodSource("com.demo.blog.categoryservice.category.datasupply.CategoryDataSupply#categories")
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
                    .withMessage(NAME_NOT_FOUND_MSG_T.formatted(noSuchCategory));
        }

        @Test
        void shouldThrowExceptionOnNullCategoryName() {
            assertThatExceptionOfType(NullPointerException.class)
                    .isThrownBy(() -> SUT.getByName(null))
                    .withMessage(NULL_NAME_MSG);
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
                    .withMessage(NULL_ID_MSG);
        }

        @Test
        void shouldThrowExceptionOnIdNotFound() {
            assertThatExceptionOfType(CategoryNotFoundException.class)
                    .isThrownBy(() -> SUT.getById(NEGATIVE_LONG))
                    .withMessage(ID_NOT_FOUND_MSG_T.formatted(NEGATIVE_LONG));
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
            Set<Category> stubbedCategories = sortedCategories().collect(Collectors.toCollection(LinkedHashSet::new));
            Set<CategoryResponse> expectedCategories = categories().map(CategoryResponse::new).collect(Collectors.toSet());
            when(repository.findByOrderByNameAsc()).thenReturn(stubbedCategories);

            // act
            Set<CategoryResponse> actual = SUT.getAll();

            // assert
            assertThat(actual.stream().toList())
                    .isSortedAccordingTo(Comparator.comparing(CategoryResponse::name))
                    .containsAll(expectedCategories);
        }
    }

    @Nested
    @TestMethodOrder(MethodOrderer.Random.class)
    class AddRequests {

        @ParameterizedTest
        @MethodSource("com.demo.blog.categoryservice.category.datasupply.CategoryDataSupply#validRequests")
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
            CategoryRequest request = ANY_CATEGORY_REQUEST;
            when(repository.existsByName(request.name())).thenReturn(true);

            // act & arrange
            assertThatExceptionOfType(CategoryAlreadyExistsException.class)
                    .isThrownBy(() -> SUT.add(request))
                    .withMessage(EXISTS_MSG_T.formatted(request.name()));
        }

        @Test
        void shouldThrowExceptionOnNullRequest() {
            assertThatExceptionOfType(NullPointerException.class)
                    .isThrownBy(() -> SUT.add(null))
                    .withMessage(NULL_REQUEST_MSG);
        }
    }

    @Nested
    @TestMethodOrder(MethodOrderer.Random.class)
    class ReplaceRequests {

        @Test
        void shouldReplaceCategoryWithNewOne() {
            // arrange
            Category categoryToReplace = new CategoryBuilder()
                    .withId(3L)
                    .withName("Testing")
                    .build();
            CategoryRequest request = new CategoryRequest("IDE");
            Category requestAsModel = new CategoryBuilder()
                    .copy(categoryToReplace)
                    .withName(new String(request.name()))
                    .build();
            long expectedId = categoryToReplace.getId().longValue();
            String expectedName = new String(request.name());
            when(repository.findById(categoryToReplace.getId())).thenReturn(Optional.of(categoryToReplace));
            when(repository.save(requestAsModel)).thenReturn(requestAsModel);

            // act
            CategoryResponse actual = SUT.replace(categoryToReplace.getId(), request);

            // assert
            assertThat(actual)
                    .hasId(expectedId)
                    .isNamed(expectedName);
            verify(repository, times(1)).save(requestAsModel);
        }

        @Test
        void shouldThrowExceptionWhenReplacingCategoryWithSameObject() {
            // arrange
            Category categoryToReplace = new CategoryBuilder()
                    .withId(1L)
                    .withName("Java")
                    .build();
            CategoryRequest request = new CategoryRequest("Java");
            Category requestAsModel = new CategoryBuilder()
                    .copy(categoryToReplace)
                    .withName("Java")
                    .build();
            when(repository.findById(1L)).thenReturn(Optional.of(categoryToReplace));
            when(repository.exists(requestAsModel)).thenReturn(true);

            // act & assert
            assertThatExceptionOfType(CategoryAlreadyExistsException.class)
                    .isThrownBy(() -> SUT.replace(categoryToReplace.getId(), request))
                    .withMessage(EXISTS_MSG_T.formatted(request.name()));
        }

        @Test
        void shouldThrowExceptionWhenReplacingNonExistingCategory() {
            // arrange
            Long nonExistingId = 8532L;
            CategoryRequest request = new CategoryRequest("Security");
            when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

            // act & assert
            assertThatExceptionOfType(CategoryNotFoundException.class)
                    .isThrownBy(() -> SUT.replace(nonExistingId, request))
                    .withMessage(ID_NOT_FOUND_MSG_T.formatted(nonExistingId));
        }

        @Test
        void shouldThrowExceptionOnNullId() {
            assertThatExceptionOfType(NullPointerException.class)
                    .isThrownBy(() -> SUT.replace(null, new CategoryRequest("ANY_STRING")))
                    .withMessage(NULL_ID_MSG);
        }

        @Test
        void shouldThrowExceptionOnNullString() {
            assertThatExceptionOfType(NullPointerException.class)
                    .isThrownBy(() -> SUT.replace(1L, null))
                    .withMessage(NULL_REQUEST_MSG);
        }
    }

    @Nested
    @TestMethodOrder(MethodOrderer.Random.class)
    class DeleteRequests {

        @Test
        void shouldDeleteEntity() {
            // arrange
            Long deleteId = 1L;
            when(repository.existsById(deleteId)).thenReturn(true);

            // act
            SUT.delete(deleteId);

            // assert
            verify(repository, times(1)).deleteById(deleteId);
        }

        @Test
        void shouldThrowExceptionOnNullId() {
            assertThatExceptionOfType(NullPointerException.class)
                    .isThrownBy(() -> SUT.delete(null))
                    .withMessage(NULL_ID_MSG);
        }
    }

    @ParameterizedTest
    @ValueSource(longs = {-2453, 0, 4366543})
    void shouldThrowExceptionOnNotFoundId(Long id) {
        assertThatExceptionOfType(CategoryNotFoundException.class)
                .isThrownBy(() -> SUT.delete(id))
                .withMessage(ID_NOT_FOUND_MSG_T.formatted(id));
    }
}
