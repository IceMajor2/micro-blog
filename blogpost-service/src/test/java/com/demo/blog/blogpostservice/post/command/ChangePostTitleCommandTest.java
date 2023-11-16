package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.post.PostRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.blogpostservice.post.datasupply.PostConstants.NULL_POST_MSG;
import static com.demo.blog.blogpostservice.post.datasupply.PostConstants.TITLE_BLANK_MSG;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.NEW_SPRING_TITLE_REQUEST;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.SPRING_POST;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
        verify(postRepository, times(1)).save(SPRING_POST);
    }

    @Test
    void shouldThrowExceptionOnPostNull() {
        // arrange
        SUT = new ChangePostTitleCommand(postRepository, null, NEW_SPRING_TITLE_REQUEST.newTitle());

        // act & assert
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> SUT.execute())
                .withMessage(NULL_POST_MSG);
    }

    @Test
    void shouldThrowExceptionOnNewTitleNull() {
        // arrange
        SUT = new ChangePostTitleCommand(postRepository, SPRING_POST, null);

        // act & assert
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> SUT.execute())
                .withMessage(TITLE_BLANK_MSG);
    }

    @Test
    void shouldSetUpdatedOnWhenSuccessful() {
        // arrange
        SUT = new ChangePostTitleCommand(postRepository, SPRING_POST, NEW_SPRING_TITLE_REQUEST.newTitle());

        // act
        SUT.execute();

        // assert
        assertThat(SPRING_POST).updatedOn(LocalDateTime.now());
        verify(postRepository, times(1)).save(SPRING_POST);
    }
}