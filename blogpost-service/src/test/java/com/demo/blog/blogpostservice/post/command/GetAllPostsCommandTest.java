package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThatPosts;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.validPostsSortedByPublishedOnDesc;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
class GetAllPostsCommandTest {

    private GetAllPostsCommand SUT;

    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        postRepository = mock(PostRepository.class);
        SUT = new GetAllPostsCommand(postRepository);
    }

    @Test
    void shouldReturnOrderedListOfAllPosts() {
        // arrange
        List<Post> stubbedPosts = validPostsSortedByPublishedOnDesc().toList();
        List<Post> expectedPosts = validPostsSortedByPublishedOnDesc().toList();
        when(postRepository.findByOrderByPublishedOnDesc()).thenReturn(stubbedPosts);

        // act
        List<Post> actual = SUT.execute();

        // assert
        assertThatPosts(actual)
                .isSortedByNewest()
                .ignoringDateFields()
                .containsAll(expectedPosts);
    }
}