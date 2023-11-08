package com.demo.blog.blogpostservice.category.command;

import com.demo.blog.blogpostservice.category.CategoryRepository;
import com.demo.blog.blogpostservice.category.exception.CategoryNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.demo.blog.blogpostservice.category.datasupply.CategoryConstants.ID_NOT_FOUND_MSG_T;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryConstants.NULL_ID_MSG;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.THREADS_CATEGORY;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
class DeleteCategoryCommandTest {

    private DeleteCategoryCommand SUT;

    private CategoryRepository repository = mock(CategoryRepository.class);

    @Test
    void shouldDeleteEntity() {
        // arrange
        Long deleteId = 1L;
        SUT = new DeleteCategoryCommand(repository, deleteId);
        when(repository.findById(deleteId)).thenReturn(Optional.of(THREADS_CATEGORY));

        // act
        SUT.execute();

        // assert
        verify(repository, times(1)).deleteById(deleteId);
    }

    @Test
    void shouldThrowExceptionOnNullId() {
        SUT = new DeleteCategoryCommand(repository, null);

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> SUT.execute())
                .withMessage(NULL_ID_MSG);
    }

    @ParameterizedTest
    @ValueSource(longs = {-2453, 0, 4366543})
    void shouldThrowExceptionOnNotFoundId(Long id) {
        SUT = new DeleteCategoryCommand(repository, id);

        assertThatExceptionOfType(CategoryNotFoundException.class)
                .isThrownBy(() -> SUT.execute())
                .withMessage(ID_NOT_FOUND_MSG_T.formatted(id));
    }
}