package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostRepository;
import com.demo.blog.blogpostservice.post.dto.PostRequest;
import com.demo.blog.blogpostservice.post.exception.PostAlreadyExistsException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static com.demo.blog.blogpostservice.author.datasupply.AuthorConstants.NULL_AUTHOR_MSG;
import static com.demo.blog.blogpostservice.author.datasupply.AuthorDataSupply.ANY_AUTHOR;
import static com.demo.blog.blogpostservice.post.datasupply.PostConstants.NULL_REQUEST_MSG;
import static com.demo.blog.blogpostservice.post.datasupply.PostConstants.TITLE_EXISTS_MSG_T;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.SPRING_POST_REQUEST;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
public class AddPostCommandTest {

    private AddPostCommand SUT;

    @Mock
    private PostRepository postRepository;

    @ParameterizedTest
    @MethodSource("com.demo.blog.blogpostservice.post.datasupply.PostDataSupply#validPostRequests")
    void shouldAddPost(PostRequest request) {
        // arrange
        Post postToSave = Post.PostBuilder.post(request)
                .writtenBy(ANY_AUTHOR.getId())
                .published(LocalDateTime.now())
                .build();

        SUT = new AddPostCommand(postRepository, request, ANY_AUTHOR);

        // act
        SUT.execute();

        // assert
        verify(postRepository, times(1)).save(refEq(postToSave, "publishedOn"));
    }

    @Test
    void shouldThrowExceptionOnPostTitleAlreadyExists() {
        // arrange
        SUT = new AddPostCommand(postRepository, SPRING_POST_REQUEST, ANY_AUTHOR);
        when(postRepository.existsByTitle(SPRING_POST_REQUEST.title())).thenReturn(true);

        // act & assert
        assertThatExceptionOfType(PostAlreadyExistsException.class)
                .isThrownBy(() -> SUT.execute())
                .withMessage(TITLE_EXISTS_MSG_T.formatted(SPRING_POST_REQUEST.title()));
    }

    @Test
    void shouldThrowExceptionOnAuthorNull() {
        // arrange
        SUT = new AddPostCommand(postRepository, SPRING_POST_REQUEST, null);

        // act & assert
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> SUT.execute())
                .withMessage(NULL_AUTHOR_MSG);
    }

    @Test
    void shouldThrowExceptionOnRequestNull() {
        // arrange
        SUT = new AddPostCommand(postRepository, null, ANY_AUTHOR);

        // act & assert
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> SUT.execute())
                .withMessage(NULL_REQUEST_MSG);
    }
}
