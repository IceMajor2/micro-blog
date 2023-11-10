package com.demo.blog.blogpostservice.category.command;

import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.category.CategoryBuilder;
import com.demo.blog.blogpostservice.category.CategoryRepository;
import com.demo.blog.blogpostservice.category.exception.CategoryAlreadyExistsException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryConstants.*;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.*;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
class ReplaceCategoryCommandTest {

    private ReplaceCategoryCommand SUT;

    private CategoryRepository repository = mock(CategoryRepository.class);

    @Test
    void shouldReplaceCategoryWithNewOne() {
        // arrange
        SUT = new ReplaceCategoryCommand(repository, THREADS_CATEGORY, CONCURRENCY_CATEGORY_REQUEST);
        Category concurrencyReplacementModel = new CategoryBuilder()
                .withId(THREADS_CATEGORY.getId().longValue())
                .withName(CONCURRENCY_CATEGORY_REQUEST.name())
                .build();

        long expectedId = THREADS_CATEGORY.getId().longValue();
        String expectedName = new String(CONCURRENCY_CATEGORY_REQUEST.name());

        when(repository.findById(THREADS_CATEGORY.getId())).thenReturn(Optional.of(THREADS_CATEGORY));
        when(repository.save(concurrencyReplacementModel)).thenReturn(concurrencyReplacementModel);

        // act
        Category actual = SUT.execute();

        // assert
        assertThat(actual)
                .hasId(expectedId)
                .isNamed(expectedName);
        verify(repository, times(1)).save(concurrencyReplacementModel);
    }

    @Test
    void shouldThrowExceptionWhenReplacingCategoryWithSameObject() {
        // arrange
        SUT = new ReplaceCategoryCommand(repository, THREADS_CATEGORY, THREADS_CATEGORY_REQUEST);
        Category threadsReplacementModel = new CategoryBuilder()
                .withId(THREADS_CATEGORY.getId().longValue())
                .withName(new String(THREADS_CATEGORY_REQUEST.name()))
                .build();

        when(repository.findById(THREADS_CATEGORY.getId())).thenReturn(Optional.of(THREADS_CATEGORY));
        when(repository.exists(threadsReplacementModel)).thenReturn(true);

        // act & assert
        assertThatExceptionOfType(CategoryAlreadyExistsException.class)
                .isThrownBy(() -> SUT.execute())
                .withMessage(EXISTS_MSG_T.formatted(threadsReplacementModel.getName()));
        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionOnNullOldCategory() {
        SUT = new ReplaceCategoryCommand(repository, null, THREADS_CATEGORY_REQUEST);

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> SUT.execute())
                .withMessage(NULL_OLD_CATEGORY_MSG);
        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionOnNullString() {
        SUT = new ReplaceCategoryCommand(repository, THREADS_CATEGORY, null);

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> SUT.execute())
                .withMessage(NULL_REQUEST_MSG);
        verify(repository, never()).save(any());
    }
}