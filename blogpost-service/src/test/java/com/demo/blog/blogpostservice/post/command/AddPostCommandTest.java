package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostBuilder;
import com.demo.blog.blogpostservice.post.PostRepository;
import com.demo.blog.blogpostservice.post.dto.PostRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.blogpostservice.author.datasupply.AuthorDataSupply.ANY_AUTHOR;
import static com.demo.blog.blogpostservice.datasupply.Constants.ANY_LONG;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
public class AddPostCommandTest {

    private AddPostCommand SUT;
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        postRepository = mock(PostRepository.class);
    }

    @ParameterizedTest
    @MethodSource("com.demo.blog.blogpostservice.post.datasupply.PostDataSupply#validPostRequests")
    void shouldAddPost(PostRequest request) {
        // arrange
        String expectedTitle = new String(request.title());
        String expectedBody = new String(request.body());
        long expectedAuthorId = ANY_AUTHOR.getId().longValue();

        Post saveStub = new PostBuilder()
                .withId(ANY_LONG)
                .withTitle(request.title())
                .withAuthor(ANY_AUTHOR.getId())
                .withBody(request.body())
                .publishedNow()
                .build();
        Post postToSave = new PostBuilder()
                .fromRequest(request)
                .withAuthor(ANY_AUTHOR.getId())
                .publishedNow()
                .build();
        when(postRepository.save(refEq(postToSave, "publishedOn"))).thenReturn(saveStub);

        SUT = new AddPostCommand(postRepository, request, ANY_AUTHOR);

        // act
        Post actual = SUT.execute();

        // assert
        assertThat(actual)
                .isTitled(expectedTitle)
                .writtenBy(expectedAuthorId)
                .hasBody(expectedBody);
    }
}
