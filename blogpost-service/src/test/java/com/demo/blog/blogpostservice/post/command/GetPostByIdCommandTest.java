package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostRepository;
import com.demo.blog.blogpostservice.post.exception.PostNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.blogpostservice.post.datasupply.PostConstants.ID_NOT_FOUND_MSG_T;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.DOCKER_POST;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
class GetPostByIdCommandTest {

    private GetPostByIdCommand SUT;

    private PostRepository postRepository = mock(PostRepository.class);

    @Test
    void shouldReturnPost() {
        // arrange
        SUT = new GetPostByIdCommand(postRepository, DOCKER_POST.getId());
        long expectedId = DOCKER_POST.getId().longValue();
        String expectedTitle = new String(DOCKER_POST.getTitle());
        String expectedBody = new String(DOCKER_POST.getBody());
        when(postRepository.findById(DOCKER_POST.getId())).thenReturn(Optional.of(DOCKER_POST));

        // act
        Post actual = SUT.execute();

        // assert
        assertThat(actual)
                .hasId(expectedId)
                .isTitled(expectedTitle)
                .hasBody(expectedBody);
    }

    @ParameterizedTest
    @ValueSource(longs = {-562349, 0, 1245643})
    void shouldThrowExceptionOnNotFoundId(long id) {
        // arrange
        SUT = new GetPostByIdCommand(postRepository, id);
        when(postRepository.findById(id)).thenReturn(Optional.empty());

        // act && assert
        assertThatExceptionOfType(PostNotFoundException.class)
                .isThrownBy(() -> SUT.execute())
                .withMessage(ID_NOT_FOUND_MSG_T.formatted(id));
    }
}