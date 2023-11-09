package com.demo.blog.blogpostservice.post;

import com.demo.blog.blogpostservice.author.command.AuthorCommandCode;
import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.category.assertion.dto.CategoryResponseAssert;
import com.demo.blog.blogpostservice.category.dto.CategoryResponse;
import com.demo.blog.blogpostservice.command.CommandFactory;
import com.demo.blog.blogpostservice.post.command.PostCommandCode;
import com.demo.blog.blogpostservice.post.dto.PostResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.blogpostservice.author.datasupply.AuthorDataSupply.ANY_AUTHOR;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.CONTAINERS_CATEGORY;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.THREADS_CATEGORY;
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
    class GetById {

        @BeforeEach
        void setUp() {
            when(commandFactory.create(PostCommandCode.GET_POST_BY_ID, DOCKER_POST.getId()).execute())
                    .thenReturn(DOCKER_POST);
            when(commandFactory.create(AuthorCommandCode.GET_AUTHOR, DOCKER_POST.getAuthor().getId()).execute())
                    .thenReturn(ANY_AUTHOR);
            when(commandFactory.create(PostCommandCode.GET_POST_CATEGORIES_SORTED_BY_NAME, DOCKER_POST.getId()).execute())
                    .thenReturn(Collections.emptyList());
        }

        @Test
        void getByIdShouldCorrectlyMapPostDTO() {
            // arrange
            long expectedId = DOCKER_POST.getId().longValue();
            String expectedTitle = new String(DOCKER_POST.getTitle());
            String expectedBody = new String(DOCKER_POST.getBody());

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

            // act
            PostResponse actual = SUT.getById(DOCKER_POST.getId());

            // assert
            assertThat(actual.author().name()).isEqualTo(expectedAuthorName);
        }

        @Test
        void shouldMapCategories() {
            // arrange
            List<CategoryResponse> expected = List.of(
                    new CategoryResponse(THREADS_CATEGORY.getId().longValue(), new String(THREADS_CATEGORY.getName())),
                    new CategoryResponse(CONTAINERS_CATEGORY.getId().longValue(), new String(CONTAINERS_CATEGORY.getName()))
            );
            List<Category> stub = List.of(CONTAINERS_CATEGORY, THREADS_CATEGORY);

            when(commandFactory.create(PostCommandCode.GET_POST_CATEGORIES_SORTED_BY_NAME, DOCKER_POST.getId()).execute())
                    .thenReturn(stub);

            // act
            PostResponse actual = SUT.getById(DOCKER_POST.getId());

            // assert
            assertThat(actual.categories())
                    .isSortedAccordingTo(CategoryResponseAssert.CATEGORY_RESPONSE_COMPARATOR)
                    .containsExactlyInAnyOrderElementsOf(expected);
        }
    }
}