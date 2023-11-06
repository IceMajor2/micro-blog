package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.post.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;

@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
class GetAllPostsCommandTest {

    private GetAllPostsCommand getAllPostsCommand;

    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        postRepository = mock(PostRepository.class);
        getAllPostsCommand = new GetAllPostsCommand(postRepository);
    }

    @Test
    void shouldReturnOrderedListOfAllPosts() {
        // arrange

    }
}