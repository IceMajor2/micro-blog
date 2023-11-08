package com.demo.blog.blogpostservice.category.command;

import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.category.CategoryBuilder;
import com.demo.blog.blogpostservice.category.CategoryRepository;
import com.demo.blog.blogpostservice.category.dto.CategoryRequest;
import com.demo.blog.blogpostservice.category.exception.CategoryAlreadyExistsException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryConstants.EXISTS_MSG_T;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryConstants.NULL_REQUEST_MSG;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.THREADS_CATEGORY_REQUEST;
import static com.demo.blog.blogpostservice.datasupply.Constants.ANY_LONG;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
class AddCategoryCommandTest {

    private AddCategoryCommand SUT;

    private CategoryRepository repository = mock(CategoryRepository.class);

    @ParameterizedTest
    @MethodSource("com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply#validCategoryRequests")
    void shouldAcceptValidCategory(CategoryRequest request) {
        // arrange
        SUT = new AddCategoryCommand(repository, request);
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
        Category actual = SUT.execute();

        // assert
        assertThat(actual).isNamed(expectedName);
        verify(repository, times(1)).save(requestAsModel);
    }

    @Test
    void shouldThrowExceptionOnConflict() {
        SUT = new AddCategoryCommand(repository, THREADS_CATEGORY_REQUEST);
        when(repository.existsByName(THREADS_CATEGORY_REQUEST.name())).thenReturn(true);

        assertThatExceptionOfType(CategoryAlreadyExistsException.class)
                .isThrownBy(() -> SUT.execute())
                .withMessage(EXISTS_MSG_T.formatted(THREADS_CATEGORY_REQUEST.name()));
    }

    @Test
    void shouldThrowExceptionOnNullRequest() {
        SUT = new AddCategoryCommand(repository, null);

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> SUT.execute())
                .withMessage(NULL_REQUEST_MSG);
    }
}