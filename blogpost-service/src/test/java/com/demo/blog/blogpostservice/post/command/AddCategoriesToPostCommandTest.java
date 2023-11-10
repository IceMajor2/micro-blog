package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostBuilder;
import com.demo.blog.blogpostservice.post.PostRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.JAVA_CATEGORY;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.SPRING_CATEGORY;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.DOCKER_POST;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
class AddCategoriesToPostCommandTest {

    private AddCategoriesToPostCommand SUT;
    private PostRepository postRepository = mock(PostRepository.class);

    @Test
    void shouldAddCategories() {
        // arrange
        Post post = new PostBuilder()
                .from(DOCKER_POST)
                .replacingCategories(List.of(JAVA_CATEGORY, SPRING_CATEGORY))
                .build();

        SUT = new AddCategoriesToPostCommand(postRepository, post, List.of(JAVA_CATEGORY, SPRING_CATEGORY));

        // act
        SUT.execute();

        // assert
        verify(postRepository, times(1)).save(post);
    }
}