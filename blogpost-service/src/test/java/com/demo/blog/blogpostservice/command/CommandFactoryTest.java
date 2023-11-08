package com.demo.blog.blogpostservice.command;

import com.demo.blog.blogpostservice.author.AuthorRepository;
import com.demo.blog.blogpostservice.post.PostRepository;
import com.demo.blog.blogpostservice.post.command.GetAllPostsCommand;
import com.demo.blog.blogpostservice.post.command.PostCommandCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;
import static org.mockito.Mockito.mock;

@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
class CommandFactoryTest {

    private CommandFactory SUT;

    private PostRepository postRepository;
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        postRepository = mock(PostRepository.class);
        authorRepository = mock(AuthorRepository.class);
        SUT = new CommandFactory(postRepository, authorRepository);
    }

    @Test
    void shouldReturnCorrectCommand() {
        // act
        Command actual = SUT.create(PostCommandCode.GET_ALL_POSTS);

        // assert
        assertThat(actual).isInstanceOf(GetAllPostsCommand.class);
    }
}