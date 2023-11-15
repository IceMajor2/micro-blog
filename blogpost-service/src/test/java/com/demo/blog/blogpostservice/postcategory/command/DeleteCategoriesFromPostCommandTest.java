package com.demo.blog.blogpostservice.postcategory.command;

import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.JAVA_CATEGORY;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.SPRING_CATEGORY;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.SPRING_POST;
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
        post = Post.PostFluentBuilder.post(SPRING_POST).setCategories(SPRING_CATEGORY, JAVA_CATEGORY).build();
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
}