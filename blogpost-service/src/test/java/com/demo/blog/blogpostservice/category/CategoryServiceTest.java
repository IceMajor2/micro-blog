package com.demo.blog.blogpostservice.category;

import com.demo.blog.blogpostservice.category.dto.CategoryRequest;
import com.demo.blog.blogpostservice.category.dto.CategoryResponse;
import com.demo.blog.blogpostservice.category.exception.CategoryAlreadyExistsException;
import com.demo.blog.blogpostservice.category.exception.CategoryNotFoundException;
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

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThatExceptionOfType;
import static com.demo.blog.blogpostservice.category.Constants.*;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.*;
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
        @MethodSource("com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply#validCategories")
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
            // arrange
            String expectedName = new String(THREADS_CATEGORY.getName());
            when(repository.findById(THREADS_CATEGORY.getId())).thenReturn(Optional.of(THREADS_CATEGORY));

            // act
            CategoryResponse actual = SUT.getById(THREADS_CATEGORY.getId());

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
            Set<Category> stubbedCategories = sortedValidCategories().collect(Collectors.toCollection(LinkedHashSet::new));
            Set<CategoryResponse> expectedCategories = validCategories().map(CategoryResponse::new).collect(Collectors.toSet());
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
        @MethodSource("com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply#validCategoryRequests")
        void shouldAcceptValidCategory(CategoryRequest request) {
            // arrange
            String expectedName = new String(request.name());
            Category stub = new CategoryBuilder()
                    .withId(ANY_LONG)
                    .withName(new String(request.name()))
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
            when(repository.existsByName(THREADS_CATEGORY_REQUEST.name())).thenReturn(true);

            // act & arrange
            assertThatExceptionOfType(CategoryAlreadyExistsException.class)
                    .isThrownBy(() -> SUT.add(THREADS_CATEGORY_REQUEST))
                    .withMessage(EXISTS_MSG_T.formatted(THREADS_CATEGORY_REQUEST.name()));
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
            Category threadsToReplace = THREADS_CATEGORY;
            CategoryRequest concurrencyAsReplacement = CONCURRENCY_CATEGORY_REQUEST;
            Category concurrencyReplacementModel = new CategoryBuilder()
                    .copy(THREADS_CATEGORY)
                    .withName(CONCURRENCY_CATEGORY_REQUEST.name())
                    .build();

            long expectedId = threadsToReplace.getId().longValue();
            String expectedName = new String(concurrencyAsReplacement.name());

            when(repository.findById(threadsToReplace.getId())).thenReturn(Optional.of(threadsToReplace));
            when(repository.save(concurrencyReplacementModel)).thenReturn(concurrencyReplacementModel);

            // act
            CategoryResponse actual = SUT.replace(threadsToReplace.getId(), concurrencyAsReplacement);

            // assert
            assertThat(actual)
                    .hasId(expectedId)
                    .isNamed(expectedName);
            verify(repository, times(1)).save(concurrencyReplacementModel);
        }

        @Test
        void shouldThrowExceptionWhenReplacingCategoryWithSameObject() {
            // arrange
            Category threadsToReplace = THREADS_CATEGORY;
            CategoryRequest threadsAsReplacement = THREADS_CATEGORY_REQUEST;
            Category threadsReplacementModel = new CategoryBuilder()
                    .copy(threadsToReplace)
                    .withName(new String(threadsAsReplacement.name()))
                    .build();

            when(repository.findById(threadsToReplace.getId())).thenReturn(Optional.of(threadsToReplace));
            when(repository.exists(threadsReplacementModel)).thenReturn(true);

            // act & assert
            assertThatExceptionOfType(CategoryAlreadyExistsException.class)
                    .isThrownBy(() -> SUT.replace(threadsToReplace.getId(), threadsAsReplacement))
                    .withMessage(EXISTS_MSG_T.formatted(threadsAsReplacement.name()));
        }

        @Test
        void shouldThrowExceptionWhenReplacingNonExistingCategory() {
            // arrange
            Long nonExistingId = 8532L;
            when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

            // act & assert
            assertThatExceptionOfType(CategoryNotFoundException.class)
                    .isThrownBy(() -> SUT.replace(nonExistingId, THREADS_CATEGORY_REQUEST))
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
