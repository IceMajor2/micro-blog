package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThatPosts;
import static com.demo.blog.blogpostservice.author.datasupply.AuthorConstants.NULL_AUTHOR_MSG;
import static com.demo.blog.blogpostservice.author.datasupply.AuthorDataSupply.JOHN_SMITH;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.ENGINEERING_POST;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.SPRING_POST;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
class GetAllPostsWrittenByCommandTest {

    private GetAllPostsWrittenByCommand SUT;

    @Mock
    private PostRepository postRepository;

    @Test
    void shouldRetrieveAllPostsInOrderWrittenByAuthor() {
        // arrange
        List<Post> expectedPosts = List.of(ENGINEERING_POST, SPRING_POST);
        SUT = new GetAllPostsWrittenByCommand(postRepository, JOHN_SMITH);

        when(postRepository.findByAuthorOrderByPublishedOnDesc(JOHN_SMITH.getId())).thenReturn(expectedPosts);

        // act
        List<Post> actual = SUT.execute();

        // assert
        assertThatPosts(actual)
                .isSortedByNewest()
                .isEqualTo(expectedPosts);
    }

    @Test
    void shouldThrowExceptionOnAuthorNull() {
        // arrange
        SUT = new GetAllPostsWrittenByCommand(postRepository, null);

        // act & assert
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> SUT.execute())
                .withMessage(NULL_AUTHOR_MSG);
    }
}