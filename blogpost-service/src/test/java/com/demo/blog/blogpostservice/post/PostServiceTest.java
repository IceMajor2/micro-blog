package com.demo.blog.blogpostservice.post;

import com.demo.blog.blogpostservice.command.CommandFactory;
import com.demo.blog.blogpostservice.post.assertion.PostResponseAssert;
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

import java.util.List;
import java.util.stream.Collectors;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.DOCKER_POST;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.validPostsSortedByPublishedOnDesc;
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

            // act
            PostResponse actual = SUT.getById(DOCKER_POST.getId());

            // assert
            assertThat(actual)
                    .hasId(expectedId)
                    .hasTitle(expectedTitle)
                    .hasBody(expectedBody);
        }

        @Test
        void shouldReturnCollectionOfAllPostsSortedByPublishDateDesc() {
            // arrange
            List<Post> stubbedPosts = validPostsSortedByPublishedOnDesc().collect(Collectors.toList());
            List<PostResponse> expectedPosts = validPostsSortedByPublishedOnDesc().map(PostResponse::new).collect(Collectors.toList());
            when(commandFactory.create(PostCommandCode.GET_ALL_POSTS).execute()).thenReturn(stubbedPosts);

            // act
            List<PostResponse> actual = SUT.getAllOrderedByPublishedDateDesc();

            // assert
            assertThat(actual)
                    .isSortedAccordingTo(PostResponseAssert.PUBLISHED_ON_COMPARATOR)
                    // ignoring dates because their difference
                    // may be very subtle comparing to stub
                    .usingRecursiveFieldByFieldElementComparatorIgnoringFields("publishedOn")
                    .usingRecursiveFieldByFieldElementComparatorIgnoringFields("updatedOn")
                    .containsAll(expectedPosts);
        }
    }
}