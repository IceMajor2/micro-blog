package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostBuilder;
import com.demo.blog.blogpostservice.post.PostRepository;
import com.demo.blog.blogpostservice.post.datasupply.PostConstants;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.blogpostservice.datasupply.Constants.ANY_STRING;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.DOCKER_POST;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.NEW_DOCKER_BODY_REQUEST;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.refEq;
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
        verify(postRepository, times(1)).save(refEq(expectedToSave, "updatedOn"));
    }

    @Test
    void shouldThrowExceptionOnBodyNull() {
        // arrange
        SUT = new ReplacePostBodyCommand(postRepository, DOCKER_POST, null);

        // act & assert
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> SUT.execute())
                .withMessage(PostConstants.BODY_BLANK_MSG);
    }

    @Test
    void shouldThrowExceptionOnPostNull() {
        // arrange
        SUT = new ReplacePostBodyCommand(postRepository, null, ANY_STRING);

        // act & assert
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> SUT.execute())
                .withMessage(PostConstants.NULL_POST_MSG);
    }

    @Test
    void shouldSetUpdatedOnWhenSuccessful() {
        // arrange
        SUT = new ReplacePostBodyCommand(postRepository, DOCKER_POST, NEW_DOCKER_BODY_REQUEST.body());

        // act
        SUT.execute();

        // assert
        assertThat(DOCKER_POST).updatedOn(LocalDateTime.now());
    }
}