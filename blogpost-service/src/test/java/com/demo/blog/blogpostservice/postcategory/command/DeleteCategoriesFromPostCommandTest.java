package com.demo.blog.blogpostservice.postcategory.command;

import com.demo.blog.blogpostservice.category.exception.CategoryNotFoundException;
import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostRepository;
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
import static com.demo.blog.blogpostservice.post.datasupply.PostConstants.NULL_POST_MSG;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.DOCKER_POST;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.SPRING_POST;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
class DeleteCategoriesFromPostCommandTest {

    private DeleteCategoriesFromPostCommand SUT;

    @Mock
    private PostRepository postRepository;

    private Post post;

    @BeforeEach
    void setUp() {
        post = Post.PostBuilder.post(SPRING_POST).setCategories(SPRING_CATEGORY, JAVA_CATEGORY).build();
    }

    @Test
    void shouldDeleteCategory() {
        // arrange
        SUT = new DeleteCategoriesFromPostCommand(postRepository, post, List.of(JAVA_CATEGORY));

        // act
        SUT.execute();

        // assert
        assertThat(post).categorizedAs(List.of(SPRING_CATEGORY));
        verify(postRepository, times(1)).save(post);
    }

    @Test
    void shouldThrowExceptionOnPostNull() {
        // arrange
        SUT = new DeleteCategoriesFromPostCommand(postRepository, null, List.of(SPRING_CATEGORY));

        // act & assert
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> SUT.execute())
                .withMessage(NULL_POST_MSG);
    }

    @Test
    void shouldThrowExceptionOnCategoryListNull() {
        // arrange
        SUT = new DeleteCategoriesFromPostCommand(postRepository, DOCKER_POST, null);

        // act & assert
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> SUT.execute())
                .withMessage(NULL_CATEGORIES_MSG);
    }

    @Test
    void shouldThrowExceptionOnCategoryListEmpty() {
        // arrange
        SUT = new DeleteCategoriesFromPostCommand(postRepository, DOCKER_POST, Collections.emptyList());

        // act & assert
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> SUT.execute())
                .withMessage(CATEGORIES_EMPTY_MSG);
    }

    @Test
    void shouldThrowExceptionWhenAllCategoriesNotExist() {
        // arrange
        SUT = new DeleteCategoriesFromPostCommand(postRepository, post, List.of(CONCURRENCY_CATEGORY, THREADS_CATEGORY));

        // act & assert
        assertThatExceptionOfType(CategoryNotFoundException.class)
                .isThrownBy(() -> SUT.execute());
    }

    @Test
    void shouldDeleteOnlyCategoriesInCommonAndDoNothingOnOthers() {
        // arrange
        SUT = new DeleteCategoriesFromPostCommand(postRepository, post, List.of(CONCURRENCY_CATEGORY, JAVA_CATEGORY));

        // act
        SUT.execute();

        // assert
        assertThat(post).categorizedAs(List.of(SPRING_CATEGORY));
        verify(postRepository, times(1)).save(post);
    }

    @Test
    void shouldSetUpdatedOnWhenDeletionSuccessful() {
        // arrange
        SUT = new DeleteCategoriesFromPostCommand(postRepository, post, List.of(SPRING_CATEGORY));

        // act
        SUT.execute();

        // assert
        assertThat(post).updatedOn(LocalDateTime.now());
        verify(postRepository, times(1)).save(post);
    }
}