package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostBuilder;
import com.demo.blog.blogpostservice.post.PostRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.demo.blog.blogpostservice.category.datasupply.CategoryConstants.NULL_CATEGORIES_MSG;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.JAVA_CATEGORY;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.SPRING_CATEGORY;
import static com.demo.blog.blogpostservice.post.datasupply.PostConstants.NULL_POST_MSG;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.DOCKER_POST;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
class AddCategoriesToPostCommandTest {

    private AddCategoriesToPostCommand SUT;
    private PostRepository postRepository = mock(PostRepository.class);

    private static final List<Category> categories = List.of(JAVA_CATEGORY, SPRING_CATEGORY);
    private static final Post post = new PostBuilder().from(DOCKER_POST).replacingCategories(categories).build();

    @Test
    void shouldAddCategories() {
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
}