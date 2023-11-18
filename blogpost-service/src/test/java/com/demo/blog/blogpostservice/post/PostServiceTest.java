package com.demo.blog.blogpostservice.post;

import com.demo.blog.blogpostservice.author.command.AuthorCommandCode;
import com.demo.blog.blogpostservice.author.dto.AuthorResponse;
import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.category.command.CategoryCommandCode;
import com.demo.blog.blogpostservice.category.dto.CategoryResponse;
import com.demo.blog.blogpostservice.category.exception.CategoryNotFoundException;
import com.demo.blog.blogpostservice.command.CommandFactory;
import com.demo.blog.blogpostservice.post.command.PostCommandCode;
import com.demo.blog.blogpostservice.post.dto.PostResponse;
import com.demo.blog.blogpostservice.postcategory.command.PostCategoryCommandCode;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.blogpostservice.author.datasupply.AuthorDataSupply.ANY_AUTHOR;
import static com.demo.blog.blogpostservice.author.datasupply.AuthorDataSupply.JOHN_SMITH;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryConstants.CATEGORY_RESPONSE_COMPARATOR;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.*;
import static com.demo.blog.blogpostservice.datasupply.Constants.ANY_LONG;
import static com.demo.blog.blogpostservice.post.datasupply.PostConstants.PUBLISHED_DESC_COMPARATOR_DTO;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.*;
import static com.demo.blog.blogpostservice.postcategory.datasupply.PostCategoryDataSupply.DOCKER_W_CONTAINERS_SPRING_REQ;
import static com.demo.blog.blogpostservice.postcategory.datasupply.PostCategoryDataSupply.SPRING_W_JAVA_SPRING_REQ;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.*;

@TestClassOrder(ClassOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostServiceImpl SUT;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private CommandFactory commandFactory;

    @Nested
    @TestMethodOrder(MethodOrderer.Random.class)
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
    @TestMethodOrder(MethodOrderer.Random.class)
    class GetCollection {

        private Post newer = Post.PostBuilder.post(DOCKER_POST)
                .setCategories(CONTAINERS_CATEGORY)
                .writtenBy(ANY_AUTHOR.getId())
                .published(LocalDateTime.now().minus(15, ChronoUnit.MINUTES))
                .build();
        private Post older = Post.PostBuilder.post(SPRING_POST)
                .setCategories(CONCURRENCY_CATEGORY, THREADS_CATEGORY)
                .writtenBy(JOHN_SMITH.getId())
                .published(LocalDateTime.now().minus(30, ChronoUnit.MINUTES))
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
    @TestMethodOrder(MethodOrderer.Random.class)
    class GetAuthorCollection {

        @BeforeEach
        void setUp() {
            List<Post> stub = List.of(ENGINEERING_POST, SPRING_POST);
            when(commandFactory.create(AuthorCommandCode.GET_AUTHOR, JOHN_SMITH.getId()).execute())
                    .thenReturn(JOHN_SMITH);
            when(commandFactory.create(PostCommandCode.GET_ALL_POSTS_OF_AUTHOR, JOHN_SMITH).execute())
                    .thenReturn(stub);
            when(commandFactory.create(PostCommandCode.GET_POST_CATEGORIES_SORTED_BY_NAME, SPRING_POST.getId()).execute())
                    .thenReturn(Collections.emptyList());
            when(commandFactory.create(PostCommandCode.GET_POST_CATEGORIES_SORTED_BY_NAME, ENGINEERING_POST.getId()).execute())
                    .thenReturn(List.of(SPRING_CATEGORY));
        }

        @Test
        void shouldMapPosts() {
            // arrange
            List<PostResponse> expectedList = List.of(
                    new PostResponse(ENGINEERING_POST.getId(), ENGINEERING_POST.getTitle(), null, null, null, null, ENGINEERING_POST.getBody()),
                    new PostResponse(SPRING_POST.getId(), SPRING_POST.getTitle(), null, null, null, null, SPRING_POST.getBody())
            );

            // act
            List<PostResponse> actual = SUT.getAllWrittenByOrderedByPublishedDateDesc(JOHN_SMITH.getId());

            // assert
            assertThat(actual)
                    .isSortedAccordingTo(PUBLISHED_DESC_COMPARATOR_DTO)
                    .usingRecursiveFieldByFieldElementComparatorOnFields("id", "title", "body")
                    .containsExactlyInAnyOrderElementsOf(expectedList);
        }
    }

    @Nested
    @TestMethodOrder(MethodOrderer.Random.class)
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

    @Nested
    @TestMethodOrder(MethodOrderer.Random.class)
    class AddCategories {

        private Post stub = Post.PostBuilder.post(DOCKER_POST).setCategories(CONTAINERS_CATEGORY, SPRING_CATEGORY).build();

        @BeforeEach
        void setUp() {
            when(commandFactory.create(PostCommandCode.GET_POST_BY_ID, DOCKER_W_CONTAINERS_SPRING_REQ.postId())
                    .execute()).thenReturn(DOCKER_POST);
            when(commandFactory.create(CategoryCommandCode.GET_CATEGORY_BY_ID, DOCKER_W_CONTAINERS_SPRING_REQ.categoryIds().get(0))
                    .execute()).thenReturn(CONTAINERS_CATEGORY);
            when(commandFactory.create(CategoryCommandCode.GET_CATEGORY_BY_ID, DOCKER_W_CONTAINERS_SPRING_REQ.categoryIds().get(1))
                    .execute()).thenReturn(SPRING_CATEGORY);
            when(commandFactory.create(AuthorCommandCode.GET_AUTHOR, DOCKER_POST.getId()).execute())
                    .thenReturn(ANY_AUTHOR);
            when(commandFactory.create(PostCommandCode.GET_POST_CATEGORIES_SORTED_BY_NAME, DOCKER_POST.getId()).execute())
                    .thenReturn(List.of(CONTAINERS_CATEGORY, SPRING_CATEGORY));
        }

        @Test
        void shouldMapPost() {
            // arrange
            long expectedId = DOCKER_POST.getId().longValue();
            String expectedTitle = new String(DOCKER_POST.getTitle());
            String expectedBody = new String(DOCKER_POST.getBody());
            PostResponse expected = new PostResponse(expectedId, expectedTitle, null, null, null, null, expectedBody);

            Post stub = Post.PostBuilder.post(DOCKER_POST).setCategories(CONTAINERS_CATEGORY, SPRING_CATEGORY).build();
            when(commandFactory.create(PostCategoryCommandCode.ADD_CATEGORIES_TO_POST, DOCKER_POST, List.of(CONTAINERS_CATEGORY, SPRING_CATEGORY))
                    .execute()).thenReturn(stub);

            // act
            PostResponse actual = SUT.addCategory(DOCKER_W_CONTAINERS_SPRING_REQ);

            // assert
            assertThat(actual)
                    .usingRecursiveComparison()
                    .comparingOnlyFields("id", "title", "body")
                    .isEqualTo(expected);
        }

        @Test
        void shouldMapAuthor() {
            // arrange
            AuthorResponse expectedAuthor = new AuthorResponse(new String(ANY_AUTHOR.getUsername()));

            when(commandFactory.create(PostCategoryCommandCode.ADD_CATEGORIES_TO_POST, DOCKER_POST, List.of(CONTAINERS_CATEGORY, SPRING_CATEGORY))
                    .execute()).thenReturn(stub);

            // act
            PostResponse actual = SUT.addCategory(DOCKER_W_CONTAINERS_SPRING_REQ);

            // assert
            assertThat(actual.author()).isEqualTo(expectedAuthor);
        }

        @Test
        void shouldMapCategories() {
            // arrange
            List<CategoryResponse> expectedCategories = List.of(
                    new CategoryResponse(CONTAINERS_CATEGORY.getId().longValue(), new String(CONTAINERS_CATEGORY.getName())),
                    new CategoryResponse(SPRING_CATEGORY.getId().longValue(), new String(SPRING_CATEGORY.getName()))
            );

            when(commandFactory.create(PostCategoryCommandCode.ADD_CATEGORIES_TO_POST, DOCKER_POST, List.of(CONTAINERS_CATEGORY, SPRING_CATEGORY))
                    .execute()).thenReturn(stub);

            // act
            PostResponse actual = SUT.addCategory(DOCKER_W_CONTAINERS_SPRING_REQ);

            // assert
            assertThat(actual.categories()).isEqualTo(expectedCategories);
        }

        @Test
        void categoryNotFoundShouldNotInterruptTheRest() {
            // arrange
            when(commandFactory.create(CategoryCommandCode.GET_CATEGORY_BY_ID, DOCKER_W_CONTAINERS_SPRING_REQ.categoryIds().get(0))
                    .execute()).thenThrow(new CategoryNotFoundException(CONTAINERS_CATEGORY.getId()));
            when(commandFactory.create(PostCategoryCommandCode.ADD_CATEGORIES_TO_POST, DOCKER_POST, List.of(SPRING_CATEGORY))
                    .execute()).thenReturn(stub);

            // act & assert
            assertThatNoException()
                    .isThrownBy(() -> SUT.addCategory(DOCKER_W_CONTAINERS_SPRING_REQ));
        }
    }

    @Nested
    @TestMethodOrder(MethodOrderer.Random.class)
    class DeleteCategories {

        Post before = Post.PostBuilder.post(SPRING_POST).setCategories(SPRING_CATEGORY).build();
        Post after = Post.PostBuilder.post(SPRING_POST).clearCategories().build();

        @BeforeEach
        void setUp() {
            when(commandFactory.create(PostCommandCode.GET_POST_BY_ID, SPRING_W_JAVA_SPRING_REQ.postId()).execute())
                    .thenReturn(before);
            when(commandFactory.create(CategoryCommandCode.GET_CATEGORY_BY_ID, JAVA_CATEGORY.getId()).execute())
                    .thenReturn(JAVA_CATEGORY);
            when(commandFactory.create(CategoryCommandCode.GET_CATEGORY_BY_ID, SPRING_CATEGORY.getId()).execute())
                    .thenReturn(SPRING_CATEGORY);
            when(commandFactory.create(AuthorCommandCode.GET_AUTHOR, SPRING_POST.getId()).execute()).thenReturn(JOHN_SMITH);
            when(commandFactory.create(PostCommandCode.GET_POST_CATEGORIES_SORTED_BY_NAME, SPRING_POST.getId())
                    .execute()).thenReturn(Collections.emptyList());
        }

        @Test
        void shouldMapPost() {
            // arrange
            PostResponse expected = new PostResponse(SPRING_POST.getId(), SPRING_POST.getTitle(), null, null, null, null, SPRING_POST.getBody());

            when(commandFactory.create(PostCategoryCommandCode.DELETE_CATEGORIES_FROM_POST, before, List.of(JAVA_CATEGORY, SPRING_CATEGORY))
                    .execute()).thenReturn(after);

            // act
            PostResponse actual = SUT.deleteCategory(SPRING_W_JAVA_SPRING_REQ);

            // assert
            assertThat(actual)
                    .usingRecursiveComparison()
                    .comparingOnlyFields("id", "name", "title")
                    .isEqualTo(expected);
        }

        @Test
        void shouldMapAuthor() {
            // arrange
            AuthorResponse expected = new AuthorResponse(JOHN_SMITH.getUsername());

            when(commandFactory.create(PostCategoryCommandCode.DELETE_CATEGORIES_FROM_POST, before, List.of(JAVA_CATEGORY, SPRING_CATEGORY))
                    .execute()).thenReturn(after);

            // act
            PostResponse actual = SUT.deleteCategory(SPRING_W_JAVA_SPRING_REQ);

            // assert
            assertThat(actual.author()).isEqualTo(expected);
        }

        @Test
        void shouldMapCategories() {
            // arrange
            when(commandFactory.create(PostCategoryCommandCode.DELETE_CATEGORIES_FROM_POST, before, List.of(JAVA_CATEGORY, SPRING_CATEGORY))
                    .execute()).thenReturn(after);

            // act
            PostResponse actual = SUT.deleteCategory(SPRING_W_JAVA_SPRING_REQ);

            // assert
            assertThat(actual.categories()).isEmpty();
        }

        @Test
        void categoryNotFoundShouldNotInterruptTheRest() {
            // arrange
            when(commandFactory.create(CategoryCommandCode.GET_CATEGORY_BY_ID, JAVA_CATEGORY.getId()).execute())
                    .thenThrow(new CategoryNotFoundException(JAVA_CATEGORY.getId()));
            when(commandFactory.create(PostCategoryCommandCode.DELETE_CATEGORIES_FROM_POST, before, List.of(SPRING_CATEGORY))
                    .execute()).thenReturn(after);

            // act & assert
            assertThatNoException()
                    .isThrownBy(() -> SUT.deleteCategory(SPRING_W_JAVA_SPRING_REQ));
        }
    }

    @Nested
    @TestMethodOrder(MethodOrderer.Random.class)
    class ReplaceBody {

        @BeforeEach
        void setUp() {
            Post stub = Post.PostBuilder.post(DOCKER_POST).withBody(NEW_DOCKER_BODY_REQUEST.body()).build();
            when(commandFactory.create(PostCommandCode.GET_POST_BY_ID, DOCKER_POST.getId())
                    .execute()).thenReturn(DOCKER_POST);
            when(commandFactory.create(PostCommandCode.REPLACE_POST_BODY, DOCKER_POST, NEW_DOCKER_BODY_REQUEST.body())
                    .execute()).thenReturn(stub);
            when(commandFactory.create(AuthorCommandCode.GET_AUTHOR, DOCKER_POST.getId()).execute())
                    .thenReturn(ANY_AUTHOR);
            when(commandFactory.create(PostCommandCode.GET_POST_CATEGORIES_SORTED_BY_NAME, DOCKER_POST.getId()).execute())
                    .thenReturn(List.of(CONTAINERS_CATEGORY));
        }

        @Test
        void shouldMapPost() {
            // arrange
            long expectedId = DOCKER_POST.getId().longValue();
            String expectedTitle = new String(DOCKER_POST.getTitle());
            String expectedBody = new String(NEW_DOCKER_BODY_REQUEST.body());
            PostResponse expected = new PostResponse(expectedId, expectedTitle, null, null, null, null, expectedBody);

            // act
            PostResponse actual = SUT.replaceBody(DOCKER_POST.getId(), NEW_DOCKER_BODY_REQUEST);

            // assert
            assertThat(actual)
                    .usingRecursiveComparison()
                    .comparingOnlyFields("id", "title", "body")
                    .isEqualTo(expected);
        }

        @Test
        void shouldMapAuthor() {
            // arrange
            AuthorResponse expectedAuthor = new AuthorResponse(new String(ANY_AUTHOR.getUsername()));

            // act
            PostResponse actual = SUT.replaceBody(DOCKER_POST.getId(), NEW_DOCKER_BODY_REQUEST);

            // assert
            assertThat(actual.author()).isEqualTo(expectedAuthor);
        }

        @Test
        void shouldMapCategories() {
            // arrange
            List<CategoryResponse> expectedCategories = List.of(
                    new CategoryResponse(CONTAINERS_CATEGORY.getId().longValue(), new String(CONTAINERS_CATEGORY.getName()))
            );

            // act
            PostResponse actual = SUT.replaceBody(DOCKER_POST.getId(), NEW_DOCKER_BODY_REQUEST);

            // assert
            assertThat(actual.categories()).isEqualTo(expectedCategories);
        }
    }

    @Nested
    @TestMethodOrder(MethodOrderer.Random.class)
    class ChangeTitle {

        @BeforeEach
        void setUp() {
            Post stub = Post.PostBuilder.post(SPRING_POST).withTitle(NEW_SPRING_TITLE_REQUEST.newTitle()).build();
            when(commandFactory.create(PostCommandCode.GET_POST_BY_ID, SPRING_POST.getId())
                    .execute()).thenReturn(SPRING_POST);
            when(commandFactory.create(PostCommandCode.CHANGE_POST_TITLE, SPRING_POST, NEW_SPRING_TITLE_REQUEST.newTitle())
                    .execute()).thenReturn(stub);
            when(commandFactory.create(AuthorCommandCode.GET_AUTHOR, SPRING_POST.getId()).execute())
                    .thenReturn(JOHN_SMITH);
            when(commandFactory.create(PostCommandCode.GET_POST_CATEGORIES_SORTED_BY_NAME, SPRING_POST.getId()).execute())
                    .thenReturn(Collections.emptyList());
        }

        @Test
        void shouldMapPost() {
            // arrange
            long expectedId = SPRING_POST.getId();
            String expectedTitle = NEW_SPRING_TITLE_REQUEST.newTitle();
            String expectedBody = SPRING_POST.getBody();
            PostResponse expected = new PostResponse(expectedId, expectedTitle, null, null, null, null, expectedBody);

            // act
            PostResponse actual = SUT.changeTitle(SPRING_POST.getId(), NEW_SPRING_TITLE_REQUEST);

            // assert
            assertThat(actual)
                    .usingRecursiveComparison()
                    .comparingOnlyFields("id", "title", "body")
                    .isEqualTo(expected);
        }

        @Test
        void shouldMapAuthor() {
            // arrange
            AuthorResponse expectedAuthor = new AuthorResponse(new String(JOHN_SMITH.getUsername()));

            // act
            PostResponse actual = SUT.changeTitle(SPRING_POST.getId(), NEW_SPRING_TITLE_REQUEST);

            // assert
            assertThat(actual.author()).isEqualTo(expectedAuthor);
        }

        @Test
        void shouldMapCategories() {
            // arrange
            List<CategoryResponse> expectedCategories = Collections.emptyList();

            // act
            PostResponse actual = SUT.changeTitle(SPRING_POST.getId(), NEW_SPRING_TITLE_REQUEST);

            // assert
            assertThat(actual.categories()).isEqualTo(expectedCategories);
        }
    }

    @Nested
    @TestMethodOrder(MethodOrderer.Random.class)
    class DeletePost {

        @Test
        void shouldExecuteDeleteCommand() {
            // arrange
            when(commandFactory.create(PostCommandCode.DELETE_POST, ANY_LONG).execute()).thenReturn(DOCKER_POST);

            // act
            SUT.delete(ANY_LONG);

            // assert
            verify(commandFactory.create(PostCommandCode.DELETE_POST, ANY_LONG), times(1)).execute();
        }
    }
}