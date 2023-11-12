package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.category.CategoryBuilder;
import com.demo.blog.blogpostservice.category.CategoryRepository;
import com.demo.blog.blogpostservice.category.assertion.CategoryAssert;
import com.demo.blog.blogpostservice.post.PostRepository;
import com.demo.blog.blogpostservice.post.exception.PostNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.Streamable;

import java.util.List;

import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.CONTAINERS_CATEGORY;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.THREADS_CATEGORY;
import static com.demo.blog.blogpostservice.post.datasupply.PostConstants.ID_NOT_FOUND_MSG_T;
import static com.demo.blog.blogpostservice.post.datasupply.PostConstants.NULL_ID_MSG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
class GetPostCategoriesCommandTest {

    private GetPostCategoriesCommand SUT;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private PostRepository postRepository;

    @Test
    void shouldReturnPostCategories() {
        // arrange
        Long stubId = 1L;
        List<Category> expected = List.of(
                new CategoryBuilder().withId(THREADS_CATEGORY.getId().longValue()).withName(new String(THREADS_CATEGORY.getName())).build(),
                new CategoryBuilder().withId(CONTAINERS_CATEGORY.getId().longValue()).withName(new String(CONTAINERS_CATEGORY.getName())).build()
        );

        SUT = new GetPostCategoriesCommand(categoryRepository, postRepository, stubId);

        when(postRepository.existsById(stubId)).thenReturn(true);
        when(categoryRepository.findByPostId(stubId))
                .thenReturn(Streamable.of(CONTAINERS_CATEGORY, THREADS_CATEGORY));


        // act
        List<Category> actual = SUT.execute();

        // assert
        assertThat(actual)
                .isSortedAccordingTo(CategoryAssert.CATEGORY_COMPARATOR)
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void shouldThrowExceptionOnNullId() {
        SUT = new GetPostCategoriesCommand(categoryRepository, postRepository, null);

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> SUT.execute())
                .withMessage(NULL_ID_MSG);
    }

    @ParameterizedTest
    @ValueSource(longs = {-6349, 0, 182487})
    void shouldThrowExceptionOnNonExistingId(Long postId) {
        SUT = new GetPostCategoriesCommand(categoryRepository, postRepository, postId);
        when(postRepository.existsById(postId)).thenReturn(false);

        assertThatExceptionOfType(PostNotFoundException.class)
                .isThrownBy(() -> SUT.execute())
                .withMessage(ID_NOT_FOUND_MSG_T.formatted(postId));
    }
}