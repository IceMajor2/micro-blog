package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.blogpostservice.category.Constants.ANY_LONG;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.DOCKER_POST;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
class GetPostCommandTest {

    private GetPostCommand SUT;

    private PostRepository postRepository = mock(PostRepository.class);

    @Test
    void shouldReturnPost() {
        // arrange
        SUT = new GetPostCommand(postRepository, ANY_LONG);
        long expectedId = DOCKER_POST.getId().longValue();
        String expectedTitle = new String(DOCKER_POST.getTitle());
        String expectedBody = new String(DOCKER_POST.getBody());
        when(postRepository.findById(DOCKER_POST.getId())).thenReturn(Optional.of(DOCKER_POST));

        // act
        Post actual = SUT.execute();

        // assert
        assertThat(actual)
                .hasId(expectedId)
                .hasTitle(expectedTitle)
                .hasBody(expectedBody);
    }
}