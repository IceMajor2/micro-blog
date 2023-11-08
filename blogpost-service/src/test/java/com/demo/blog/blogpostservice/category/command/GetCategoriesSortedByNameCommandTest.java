package com.demo.blog.blogpostservice.category.command;

import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.category.CategoryRepository;
import com.demo.blog.blogpostservice.category.assertion.CategoryAssert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.Streamable;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.sortedValidCategories;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.validCategories;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
class GetCategoriesSortedByNameCommandTest {

    private GetCategoriesSortedByNameCommand SUT;

    private CategoryRepository repository = mock(CategoryRepository.class);

    @Test
    void shouldReturnEmptyListWhenCollectionIsEmpty() {
        // arrange
        SUT = new GetCategoriesSortedByNameCommand(repository);
        when(repository.findByOrderByNameAsc()).thenReturn(Collections.emptySet());

        // act
        Iterable<Category> actual = SUT.execute();

        // assert
        assertThat(actual).isEmpty();
    }

    @Test
    void shouldReturnListOfAllCategoriesSortedInAlphabeticOrder() {
        // arrange
        SUT = new GetCategoriesSortedByNameCommand(repository);
        List<Category> stubbedCategories = sortedValidCategories().collect(Collectors.toList());
        List<Category> expectedCategories = validCategories().collect(Collectors.toList());
        when(repository.findByOrderByNameAsc()).thenReturn(stubbedCategories);

        // act
        Iterable<Category> actual = SUT.execute();

        // assert
        assertThat(Streamable.of(actual).stream().toList())
                .isSortedAccordingTo(CategoryAssert.CATEGORY_COMPARATOR)
                .containsAll(expectedCategories);
    }
}