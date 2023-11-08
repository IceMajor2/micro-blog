package com.demo.blog.blogpostservice.category.command;

import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.category.CategoryRepository;
import com.demo.blog.blogpostservice.category.exception.CategoryNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryConstants.ID_NOT_FOUND_MSG_T;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryConstants.NULL_ID_MSG;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.THREADS_CATEGORY;
import static com.demo.blog.blogpostservice.datasupply.Constants.NEGATIVE_LONG;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
class GetCategoryByIdCommandTest {

    private GetCategoryByIdCommand SUT;

    private CategoryRepository repository = mock(CategoryRepository.class);

    @Test
    void shouldReturnCategory() {
        // arrange
        SUT = new GetCategoryByIdCommand(repository, THREADS_CATEGORY.getId());
        String expectedName = new String(THREADS_CATEGORY.getName());
        when(repository.findById(THREADS_CATEGORY.getId())).thenReturn(Optional.of(THREADS_CATEGORY));

        // act
        Category actual = SUT.execute();

        // assert
        assertThat(actual).isNamed(expectedName);
    }

    @Test
    void shouldThrowExceptionOnNullId() {
        SUT = new GetCategoryByIdCommand(repository, null);

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> SUT.execute())
                .withMessage(NULL_ID_MSG);
    }

    @Test
    void shouldThrowExceptionOnIdNotFound() {
        SUT = new GetCategoryByIdCommand(repository, NEGATIVE_LONG);

        assertThatExceptionOfType(CategoryNotFoundException.class)
                .isThrownBy(() -> SUT.execute())
                .withMessage(ID_NOT_FOUND_MSG_T.formatted(NEGATIVE_LONG));
    }
}