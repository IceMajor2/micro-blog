package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.post.PostRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.NEW_SPRING_TITLE_REQUEST;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.SPRING_POST;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
class ChangePostTitleCommandTest {

    private ChangePostTitleCommand SUT;

    @Mock
    private PostRepository postRepository;

    @Test
    void shouldUpdatePostTitle() {
        // arrange
        SUT = new ChangePostTitleCommand(postRepository, SPRING_POST, NEW_SPRING_TITLE_REQUEST.newTitle());

        // act
        SUT.execute();

        // assert
        assertThat(SPRING_POST).isTitled(NEW_SPRING_TITLE_REQUEST.newTitle());
    }
}