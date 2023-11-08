package com.demo.blog.blogpostservice.category.command;

import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.category.CategoryRepository;
import com.demo.blog.blogpostservice.category.exception.CategoryNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryConstants.NAME_NOT_FOUND_MSG_T;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryConstants.NULL_NAME_MSG;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
class GetCategoryByNameCommandTest {

    private GetCategoryByNameCommand SUT;

    private CategoryRepository repository = mock(CategoryRepository.class);

    @ParameterizedTest
    @MethodSource("com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply#validCategories")
    void shouldReturnCategoryOnGetWithParameter(Category category) {
        // arrange
        SUT = new GetCategoryByNameCommand(repository, category.getName());
        String expectedName = new String(category.getName());
        when(repository.findByName(category.getName())).thenReturn(Optional.of(category));

        // act
        Category actual = SUT.execute();

        // assert
        assertThat(actual).isNamed(expectedName);
    }

    @Test
    void shouldThrowExceptionOnNullCategoryName() {
        SUT = new GetCategoryByNameCommand(repository, null);

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> SUT.execute())
                .withMessage(NULL_NAME_MSG);
    }

    @Test
    void shouldThrowExceptionOnCategoryNotFound() {
        String noSuchCategory = "NON_EXISTING_CATEGORY";
        SUT = new GetCategoryByNameCommand(repository, noSuchCategory);

        assertThatExceptionOfType(CategoryNotFoundException.class)
                .isThrownBy(() -> SUT.execute())
                .withMessage(NAME_NOT_FOUND_MSG_T.formatted(noSuchCategory));
    }
}