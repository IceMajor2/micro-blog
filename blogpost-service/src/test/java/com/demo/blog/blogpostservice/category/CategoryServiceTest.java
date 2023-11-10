package com.demo.blog.blogpostservice.category;

import com.demo.blog.blogpostservice.category.command.CategoryCommandCode;
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

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.CONCURRENCY_CATEGORY;
import static org.mockito.Mockito.when;

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
        assertThat(actual)
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "name")
                .isEqualTo(expected);
    }

    @Test
    void getByIdShouldMapCategory() {
        long expectedId = CONCURRENCY_CATEGORY.getId().longValue();
        String expectedName = new String(CONCURRENCY_CATEGORY.getName());
        CategoryResponse expected = new CategoryResponse(expectedId, expectedName);

        when(commandFactory.create(CategoryCommandCode.GET_CATEGORY_BY_ID, CONCURRENCY_CATEGORY.getId()).execute())
                .thenReturn(CONCURRENCY_CATEGORY);

        // act
        CategoryResponse actual = SUT.getById(CONCURRENCY_CATEGORY.getId());

        // assert
        assertThat(actual)
                .usingRecursiveComparison()
                .comparingOnlyFields("id", "name")
                .isEqualTo(expected);
    }
}