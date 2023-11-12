package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostBuilder;
import com.demo.blog.blogpostservice.post.PostRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.DOCKER_POST;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.NEW_DOCKER_BODY_REQUEST;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
class ReplacePostBodyCommandTest {

    private ReplacePostBodyCommand SUT;

    @Mock
    private PostRepository postRepository;

    @Test
    void shouldReplacePreviousBody() {
        // arrange
        Post expectedToSave = new PostBuilder().from(DOCKER_POST).withBody(NEW_DOCKER_BODY_REQUEST.body()).build();
        SUT = new ReplacePostBodyCommand(postRepository, DOCKER_POST, NEW_DOCKER_BODY_REQUEST.body());

        // act
        SUT.execute();

        // assert
        verify(postRepository, times(1)).save(expectedToSave);
    }
}