package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.post.PostRepository;
import com.demo.blog.blogpostservice.post.exception.PostNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.demo.blog.blogpostservice.datasupply.Constants.ANY_LONG;
import static com.demo.blog.blogpostservice.post.datasupply.PostConstants.ID_NOT_FOUND_MSG_T;
import static com.demo.blog.blogpostservice.post.datasupply.PostConstants.NULL_ID_MSG;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.DOCKER_POST;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
class DeletePostCommandTest {

    private DeletePostCommand SUT;

    @Mock
    private PostRepository postRepository;

    @Test
    void shouldDeletePost() {
        // arrange
        SUT = new DeletePostCommand(postRepository, ANY_LONG);
        when(postRepository.findById(ANY_LONG)).thenReturn(Optional.of(DOCKER_POST));

        // act
        SUT.execute();

        // assert
        verify(postRepository, times(1)).deleteById(ANY_LONG);
    }

    @Test
    void shouldThrowExceptionOnIdNotFound() {
        // arrange
        SUT = new DeletePostCommand(postRepository, ANY_LONG);
        when(postRepository.findById(ANY_LONG)).thenReturn(Optional.empty());

        // act & assert
        assertThatExceptionOfType(PostNotFoundException.class)
                .isThrownBy(() -> SUT.execute())
                .withMessage(ID_NOT_FOUND_MSG_T.formatted(ANY_LONG));
    }

    @Test
    void shouldThrowExceptionOnIdNull() {
        // arrange
        SUT = new DeletePostCommand(postRepository, null);

        // act & assert
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> SUT.execute())
                .withMessage(NULL_ID_MSG);
    }
}