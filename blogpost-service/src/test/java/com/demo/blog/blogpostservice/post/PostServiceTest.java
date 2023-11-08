package com.demo.blog.blogpostservice.post;

import com.demo.blog.blogpostservice.author.command.AuthorCommandCode;
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

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.blogpostservice.author.datasupply.AuthorDataSupply.ANY_AUTHOR;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.DOCKER_POST;
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
        void shouldReturnPost() {
            // arrange
            long expectedId = DOCKER_POST.getId().longValue();
            String expectedTitle = new String(DOCKER_POST.getTitle());
            String expectedBody = new String(DOCKER_POST.getBody());

            when(commandFactory.create(PostCommandCode.GET_POST, DOCKER_POST.getId()).execute())
                    .thenReturn(DOCKER_POST);
            when(commandFactory.create(AuthorCommandCode.GET_AUTHOR, DOCKER_POST.getAuthor().getId()).execute())
                    .thenReturn(ANY_AUTHOR);

            // act
            PostResponse actual = SUT.getById(DOCKER_POST.getId());

            // assert
            assertThat(actual.id()).isEqualTo(expectedId);
            assertThat(actual.title()).isEqualTo(expectedTitle);
            assertThat(actual.body()).isEqualTo(expectedBody);
        }

        @Test
        void shouldMapAuthor() {
            // arrange
            String expectedAuthorName = new String(ANY_AUTHOR.getUsername());

            when(commandFactory.create(PostCommandCode.GET_POST, DOCKER_POST.getId()).execute())
                    .thenReturn(DOCKER_POST);
            when(commandFactory.create(AuthorCommandCode.GET_AUTHOR, DOCKER_POST.getAuthor().getId()).execute())
                    .thenReturn(ANY_AUTHOR);

            // act
            PostResponse actual = SUT.getById(DOCKER_POST.getId());

            // assert
            assertThat(actual.author().name()).isEqualTo(expectedAuthorName);
        }

        @Test
        void shouldMapCategories() {
            // arrange
        }
    }
}