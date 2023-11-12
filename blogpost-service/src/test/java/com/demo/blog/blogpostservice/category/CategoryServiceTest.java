package com.demo.blog.blogpostservice.category;

import com.demo.blog.blogpostservice.category.command.CategoryCommandCode;
import com.demo.blog.blogpostservice.category.datasupply.CategoryConstants;
import com.demo.blog.blogpostservice.category.dto.CategoryResponse;
import com.demo.blog.blogpostservice.command.CommandFactory;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.*;
import static org.mockito.Mockito.*;

@TestClassOrder(ClassOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryServiceImpl SUT;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private CommandFactory commandFactory;

    @Test
    void getByNameShouldMapCategory() {
        // arrange
        long expectedId = CONCURRENCY_CATEGORY.getId().longValue();
        String expectedName = new String(CONCURRENCY_CATEGORY.getName());
        CategoryResponse expected = new CategoryResponse(expectedId, expectedName);

        when(commandFactory.create(CategoryCommandCode.GET_CATEGORY_BY_NAME, CONCURRENCY_CATEGORY.getName()).execute())
                .thenReturn(CONCURRENCY_CATEGORY);

        // act
        CategoryResponse actual = SUT.getByName(CONCURRENCY_CATEGORY.getName());

        // assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getByIdShouldMapCategory() {
        // arrange
        long expectedId = CONCURRENCY_CATEGORY.getId().longValue();
        String expectedName = new String(CONCURRENCY_CATEGORY.getName());
        CategoryResponse expected = new CategoryResponse(expectedId, expectedName);

        when(commandFactory.create(CategoryCommandCode.GET_CATEGORY_BY_ID, CONCURRENCY_CATEGORY.getId()).execute())
                .thenReturn(CONCURRENCY_CATEGORY);

        // act
        CategoryResponse actual = SUT.getById(CONCURRENCY_CATEGORY.getId());

        // assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getAllOrderedByNameShouldMapCategory() {
        // arrange
        List<Category> stub = List.of(CONTAINERS_CATEGORY, THREADS_CATEGORY);
        List<CategoryResponse> expected = List.of(
                new CategoryResponse(CONTAINERS_CATEGORY.getId().longValue(), new String(CONTAINERS_CATEGORY.getName())),
                new CategoryResponse(THREADS_CATEGORY.getId().longValue(), new String(THREADS_CATEGORY.getName()))
        );

        when(commandFactory.create(CategoryCommandCode.GET_CATEGORIES_SORTED_BY_NAME).execute())
                .thenReturn(stub);

        // act
        List<CategoryResponse> actual = SUT.getAllOrderedByName();

        // assert
        assertThat(actual)
                .isSortedAccordingTo(CategoryConstants.CATEGORY_RESPONSE_COMPARATOR)
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void addShouldMapCategory() {
        // arrange
        CategoryResponse expected = new CategoryResponse(THREADS_CATEGORY.getId().longValue(), new String(THREADS_CATEGORY.getName()));

        when(commandFactory.create(CategoryCommandCode.ADD_CATEGORY, THREADS_CATEGORY_REQUEST).execute())
                .thenReturn(THREADS_CATEGORY);

        // act
        CategoryResponse actual = SUT.add(THREADS_CATEGORY_REQUEST);

        // assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void replaceShouldMapCategory() {
        // arrange
        Long replaceId = THREADS_CATEGORY.getId();
        CategoryResponse expected = new CategoryResponse(THREADS_CATEGORY.getId().longValue(), new String(THREADS_CATEGORY.getName()));

        when(commandFactory.create(CategoryCommandCode.GET_CATEGORY_BY_ID, replaceId).execute())
                .thenReturn(CONTAINERS_CATEGORY);
        when(commandFactory.create(CategoryCommandCode.REPLACE_CATEGORY, CONTAINERS_CATEGORY, THREADS_CATEGORY_REQUEST).execute())
                .thenReturn(THREADS_CATEGORY);

        // act
        CategoryResponse actual = SUT.replace(replaceId, THREADS_CATEGORY_REQUEST);

        // assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldExecuteDeleteCommand() {
        // arrange
        when(commandFactory.create(CategoryCommandCode.DELETE_CATEGORY, CONTAINERS_CATEGORY.getId()).execute())
                .thenReturn(CONTAINERS_CATEGORY);

        // act
        SUT.delete(CONTAINERS_CATEGORY.getId());

        // verify
        verify(commandFactory.create(CategoryCommandCode.DELETE_CATEGORY, CONTAINERS_CATEGORY.getId()), times(1))
                .execute();
    }
}