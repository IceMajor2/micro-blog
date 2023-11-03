package com.demo.blog.blogpostservice.post;

import com.demo.blog.blogpostservice.command.CommandFactory;
import com.demo.blog.blogpostservice.post.command.PostCommandCode;
import com.demo.blog.blogpostservice.post.dto.PostResponse;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@TestClassOrder(ClassOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostServiceImpl SUT;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private CommandFactory commandFactory;

    @Nested
    class GetRequests {

        @Test
        void shouldReturnCollectionOfAllPosts() {
            // arrange
            Set<Post> stubbedPosts = Set.of(
                    new Post("Title", "Body", AggregateReference.to(5L)),
                    new Post("Title DOS", "Body X", AggregateReference.to(1L))
            );
            Set<PostResponse> expectedPosts = Set.of(
                    new PostResponse(null, "Title", "Body"),
                    new PostResponse(null, "Title DOS", "Body X")
            );
            when(commandFactory.create(PostCommandCode.GET_ALL_POSTS).execute()).thenReturn(stubbedPosts);

            // act
            Set<PostResponse> actual = SUT.getAll();

            // assert
            assertThat(actual).containsExactlyElementsOf(expectedPosts);
        }
    }
}