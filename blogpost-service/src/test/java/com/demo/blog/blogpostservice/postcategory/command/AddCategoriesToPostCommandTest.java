package com.demo.blog.blogpostservice.postcategory.command;

import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostRepository;
import com.demo.blog.blogpostservice.post.exception.PostAlreadyCategorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryConstants.CATEGORIES_EMPTY_MSG;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryConstants.NULL_CATEGORIES_MSG;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.*;
import static com.demo.blog.blogpostservice.post.datasupply.PostConstants.ALREADY_CATEGORIZED_AS;
import static com.demo.blog.blogpostservice.post.datasupply.PostConstants.NULL_POST_MSG;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.DOCKER_POST;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
class AddCategoriesToPostCommandTest {

    private AddCategoriesToPostCommand SUT;
    @Mock
    private PostRepository postRepository;

    private List<Category> categories;
    private Post post;

    @BeforeEach
    void setUp() {
        post = Post.PostFluentBuilder.post(DOCKER_POST).clearCategories().build();
        categories = List.of(JAVA_CATEGORY, SPRING_CATEGORY);
    }

    @Test
    void shouldAddCategories() {
        // arrange
        SUT = new AddCategoriesToPostCommand(postRepository, post, categories);

        // act
        SUT.execute();

        // assert
        verify(postRepository, times(1)).save(post);
    }

    @Test
    void shouldThrowExceptionOnPostNull() {
        // arrange
        SUT = new AddCategoriesToPostCommand(postRepository, null, categories);

        // act & assert
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> SUT.execute())
                .withMessage(NULL_POST_MSG);
    }

    @Test
    void shouldThrowExceptionOnCategoriesNull() {
        // arrange
        SUT = new AddCategoriesToPostCommand(postRepository, post, null);

        // act & assert
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> SUT.execute())
                .withMessage(NULL_CATEGORIES_MSG);
    }

    @Test
    void shouldThrowExceptionOnCategoriesEmpty() {
        // arrange
        SUT = new AddCategoriesToPostCommand(postRepository, post, Collections.emptyList());

        // act & assert
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> SUT.execute())
                .withMessage(CATEGORIES_EMPTY_MSG);
    }

    @Test
    void shouldThrowExceptionWhenNoNewCategoryIsAdded() {
        // arrange
        post = Post.PostFluentBuilder.post(post).setCategories(categories).build();
        SUT = new AddCategoriesToPostCommand(postRepository, post, categories);

        // act & assert
        assertThatExceptionOfType(PostAlreadyCategorizedException.class)
                .isThrownBy(() -> SUT.execute())
                .withMessage(ALREADY_CATEGORIZED_AS.formatted(JAVA_CATEGORY.getName() + ", " + SPRING_CATEGORY.getName()));
        verify(postRepository, never()).save(any());
    }

    @Test
    void shouldSetUpdatedOnWhenSuccessfullyAddingCategory() {
        // arrange
        SUT = new AddCategoriesToPostCommand(postRepository, post, categories);

        // act
        SUT.execute();

        // assert
        assertThat(post).updatedOn(LocalDateTime.now());
    }

    @Test
    void shouldAddOnlyNewCategoriesAndDoNothingOnAlreadyExistingOnes() {
        // arrange
        post = Post.PostFluentBuilder.post(DOCKER_POST).setCategories(categories).build();
        List<Category> newCategories = List.of(CONCURRENCY_CATEGORY, SPRING_CATEGORY);
        SUT = new AddCategoriesToPostCommand(postRepository, post, newCategories);

        // act
        SUT.execute();

        // assert
        verify(postRepository, times(1)).save(post);
    }
}