package com.demo.blog.blogpostservice.post;

import com.demo.blog.blogpostservice.author.command.AuthorCommandCode;
import com.demo.blog.blogpostservice.author.dto.AuthorResponse;
import com.demo.blog.blogpostservice.category.Category;
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
import static com.demo.blog.blogpostservice.author.datasupply.AuthorDataSupply.JOHN_SMITH;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryConstants.CATEGORY_RESPONSE_COMPARATOR;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.*;
import static com.demo.blog.blogpostservice.post.datasupply.PostConstants.PUBLISHED_DESC_COMPARATOR_DTO;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.*;
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
        void shouldMapPost() {
            // arrange
            long expectedId = DOCKER_POST.getId().longValue();
            String expectedTitle = new String(DOCKER_POST.getTitle());
            String expectedBody = new String(DOCKER_POST.getBody());
            PostResponse expected = new PostResponse(expectedId, expectedTitle, null, null, null, null, expectedBody);

            // act
            PostResponse actual = SUT.getById(DOCKER_POST.getId());

            // assert
            assertThat(actual)
                    .usingRecursiveComparison()
                    .comparingOnlyFields("id", "title", "body")
                    .isEqualTo(expected);
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
                    .isNotEmpty()
                    .isSortedAccordingTo(CATEGORY_RESPONSE_COMPARATOR)
                    .containsExactlyInAnyOrderElementsOf(expected);
        }
    }

    @Nested
    class GetCollection {

        private Post newer = new PostBuilder()
                .from(DOCKER_POST)
                .replacingCategories(List.of(CONTAINERS_CATEGORY))
                .withAuthor(ANY_AUTHOR.getId())
                .publishedFifteenMinsAgo()
                .build();
        private Post older = new PostBuilder()
                .from(SPRING_POST)
                .replacingCategories(List.of(CONCURRENCY_CATEGORY, THREADS_CATEGORY))
                .withAuthor(JOHN_SMITH.getId())
                .publishedThirtyMinsAgo()
                .build();

        @BeforeEach
        void setUp() {
            List<Post> stub = List.of(newer, older);
            when(commandFactory.create(PostCommandCode.GET_ALL_POSTS).execute()).thenReturn(stub);
            when(commandFactory.create(AuthorCommandCode.GET_AUTHOR, newer.getId()).execute())
                    .thenReturn(ANY_AUTHOR);
            when(commandFactory.create(AuthorCommandCode.GET_AUTHOR, older.getId()).execute())
                    .thenReturn(JOHN_SMITH);
            when(commandFactory.create(PostCommandCode.GET_POST_CATEGORIES_SORTED_BY_NAME, newer.getId()).execute())
                    .thenReturn(List.of(CONTAINERS_CATEGORY));
            when(commandFactory.create(PostCommandCode.GET_POST_CATEGORIES_SORTED_BY_NAME, older.getId()).execute())
                    .thenReturn(List.of(CONCURRENCY_CATEGORY, THREADS_CATEGORY));
        }

        @Test
        void shouldMapPostInList() {
            // arrange
            List<PostResponse> expected = List.of(
                    new PostResponse(older.getId().longValue(), new String(older.getTitle()), null, null, null, null, new String(older.getBody())),
                    new PostResponse(newer.getId().longValue(), new String(newer.getTitle()), null, null, null, null, new String(newer.getBody()))
            );

            // act
            List<PostResponse> actual = SUT.getAllOrderedByPublishedDateDesc();

            // assert
            assertThat(actual)
                    .isSortedAccordingTo(PUBLISHED_DESC_COMPARATOR_DTO)
                    .usingRecursiveFieldByFieldElementComparatorOnFields("id", "title", "body")
                    .containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        void shouldMapAuthorsInPostList() {
            // arrange
            List<AuthorResponse> expected = List.of(new AuthorResponse(JOHN_SMITH), new AuthorResponse(ANY_AUTHOR));

            // act
            List<PostResponse> actual = SUT.getAllOrderedByPublishedDateDesc();

            // assert
            assertThat(actual)
                    .map(PostResponse::author)
                    .containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        void shouldMapCategoriesInPostList() {
            // arrange
            List<List<CategoryResponse>> expected = List.of(
                    List.of(
                            new CategoryResponse(null, new String(CONCURRENCY_CATEGORY.getName())),
                            new CategoryResponse(null, new String(THREADS_CATEGORY.getName()))
                    ),
                    List.of(
                            new CategoryResponse(null, new String(CONTAINERS_CATEGORY.getName()))
                    )
            );

            // act
            List<PostResponse> actual = SUT.getAllOrderedByPublishedDateDesc();

            // assert
            assertThat(actual)
                    .map(PostResponse::categories)
                    .usingRecursiveFieldByFieldElementComparatorOnFields("name")
                    .allSatisfy(list -> assertThat(list).isSortedAccordingTo(CATEGORY_RESPONSE_COMPARATOR))
                    .containsExactlyElementsOf(expected);
        }
    }

    @Nested
    class Add {

        @BeforeEach
        void setUp() {
            when(commandFactory.create(PostCommandCode.ADD_POST, DOCKER_POST_REQUEST, JOHN_SMITH).execute())
                    .thenReturn(DOCKER_POST);
        }

        @Test
        void shouldMapPost() {
            // arrange
            long expectedId = DOCKER_POST.getId().longValue();
            String expectedTitle = new String(DOCKER_POST.getTitle());
            String expectedBody = new String(DOCKER_POST.getBody());
            PostResponse expected = new PostResponse(expectedId, expectedTitle, null, null, null, null, expectedBody);

            // act
            PostResponse actual = SUT.add(DOCKER_POST_REQUEST, JOHN_SMITH);

            // assert
            assertThat(actual)
                    .usingRecursiveComparison()
                    .comparingOnlyFields("id", "title", "body")
                    .isEqualTo(expected);
        }

        @Test
        void shouldMapAuthor() {
            // arrange
            AuthorResponse expected = new AuthorResponse(new String(JOHN_SMITH.getUsername()));

            // act
            PostResponse actual = SUT.add(DOCKER_POST_REQUEST, JOHN_SMITH);

            // assert
            assertThat(actual.author()).isEqualTo(expected);
        }

        @Test
        void shouldMapEmptyCategoryList() {
            // arrange
            List<CategoryResponse> expected = Collections.emptyList();

            // act
            PostResponse actual = SUT.add(DOCKER_POST_REQUEST, JOHN_SMITH);

            // assert
            assertThat(actual.categories()).isEqualTo(expected);
        }
    }
}