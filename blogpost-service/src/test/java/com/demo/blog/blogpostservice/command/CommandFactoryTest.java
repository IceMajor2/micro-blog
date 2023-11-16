package com.demo.blog.blogpostservice.command;

import com.demo.blog.blogpostservice.author.AuthorRepository;
import com.demo.blog.blogpostservice.category.CategoryRepository;
import com.demo.blog.blogpostservice.category.command.*;
import com.demo.blog.blogpostservice.post.PostRepository;
import com.demo.blog.blogpostservice.post.command.*;
import com.demo.blog.blogpostservice.postcategory.command.AddCategoriesToPostCommand;
import com.demo.blog.blogpostservice.postcategory.command.DeleteCategoriesFromPostCommand;
import com.demo.blog.blogpostservice.postcategory.command.PostCategoryCommandCode;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.blogpostservice.author.datasupply.AuthorDataSupply.JOHN_SMITH;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.*;
import static com.demo.blog.blogpostservice.datasupply.Constants.ANY_LONG;
import static com.demo.blog.blogpostservice.datasupply.Constants.ANY_STRING;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.*;

@TestClassOrder(ClassOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
class CommandFactoryTest {

    @InjectMocks
    private CommandFactory SUT;

    @Mock
    private PostRepository postRepository;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private CategoryRepository categoryRepository;

    @Nested
    @TestMethodOrder(MethodOrderer.Random.class)
    class CategoryCommand {

        @Test
        void shouldReturnGetCategoryByIdCommand() {
            // act
            Command actual = SUT.create(CategoryCommandCode.GET_CATEGORY_BY_ID, ANY_LONG);

            // assert
            assertThat(actual).isInstanceOf(GetCategoryByIdCommand.class);
        }

        @Test
        void shouldReturnGetCategoryByNameCommand() {
            // act
            Command actual = SUT.create(CategoryCommandCode.GET_CATEGORY_BY_NAME, ANY_STRING);

            // assert
            assertThat(actual).isInstanceOf(GetCategoryByNameCommand.class);
        }

        @Test
        void shouldReturnGetCategoriesSortedByNameCommand() {
            // act
            Command actual = SUT.create(CategoryCommandCode.GET_CATEGORIES_SORTED_BY_NAME);

            // assert
            assertThat(actual).isInstanceOf(GetCategoriesSortedByNameCommand.class);
        }

        @Test
        void shouldReturnAddCategoryCommand() {
            // act
            Command actual = SUT.create(CategoryCommandCode.ADD_CATEGORY, THREADS_CATEGORY_REQUEST);

            // assert
            assertThat(actual).isInstanceOf(AddCategoryCommand.class);
        }

        @Test
        void shouldReturnReplaceCategoryCommand() {
            // act
            Command actual = SUT.create(CategoryCommandCode.REPLACE_CATEGORY, THREADS_CATEGORY, CONCURRENCY_CATEGORY_REQUEST);

            // assert
            assertThat(actual).isInstanceOf(ReplaceCategoryCommand.class);
        }

        @Test
        void shouldReturnDeleteCategoryCommand() {
            // act
            Command actual = SUT.create(CategoryCommandCode.DELETE_CATEGORY, ANY_LONG);

            // assert
            assertThat(actual).isInstanceOf(DeleteCategoryCommand.class);
        }
    }

    @Nested
    class PostCommand {

        @Test
        void shouldReturnGetAllPostsCommand() {
            // act
            Command actual = SUT.create(PostCommandCode.GET_ALL_POSTS);

            // assert
            assertThat(actual).isInstanceOf(GetAllPostsCommand.class);
        }

        @Test
        void shouldReturnGetPostByIdCommand() {
            // act
            Command actual = SUT.create(PostCommandCode.GET_POST_BY_ID, ANY_LONG);

            // assert
            assertThat(actual).isInstanceOf(GetPostByIdCommand.class);
        }

        @Test
        void shouldReturnGetPostCategoriesSortedByNameCommand() {
            // act
            Command actual = SUT.create(PostCommandCode.GET_POST_CATEGORIES_SORTED_BY_NAME, ANY_LONG);

            // assert
            assertThat(actual).isInstanceOf(GetPostCategoriesCommand.class);
        }

        @Test
        void shouldReturnAddPostCommand() {
            // act
            Command actual = SUT.create(PostCommandCode.ADD_POST, SPRING_POST_REQUEST, JOHN_SMITH);

            // assert
            assertThat(actual).isInstanceOf(AddPostCommand.class);
        }

        @Test
        void shouldReturnReplacePostBodyCommand() {
            // act
            Command actual = SUT.create(PostCommandCode.REPLACE_POST_BODY, DOCKER_POST, NEW_DOCKER_BODY_REQUEST.body());

            // assert
            assertThat(actual).isInstanceOf(ReplacePostBodyCommand.class);
        }

        @Test
        void shouldReturnChangePostTitleCommand() {
            // act
            Command actual = SUT.create(PostCommandCode.CHANGE_POST_TITLE, SPRING_POST, NEW_SPRING_TITLE_REQUEST);

            // assert
            assertThat(actual).isInstanceOf(ChangePostTitleCommand.class);
        }

        @Test
        void shouldReturnDeletePostCommand() {
            // act
            Command actual = SUT.create(PostCommandCode.DELETE_POST, ANY_LONG);

            // assert
            assertThat(actual).isInstanceOf(DeletePostCommand.class);
        }
    }

    @Nested
    class PostCategoryCommand {

        @Test
        void shouldReturnAddCategoriesToPostCommand() {
            // act
            Command actual = SUT.create(PostCategoryCommandCode.ADD_CATEGORIES_TO_POST, SPRING_POST, List.of(SPRING_CATEGORY));

            // assert
            assertThat(actual).isInstanceOf(AddCategoriesToPostCommand.class);
        }

        @Test
        void shouldReturnDeleteCategoriesFromPostCommand() {
            // act
            Command actual = SUT.create(PostCategoryCommandCode.DELETE_CATEGORIES_FROM_POST, SPRING_POST, List.of(SPRING_CATEGORY));

            // assert
            assertThat(actual).isInstanceOf(DeleteCategoriesFromPostCommand.class);
        }
    }
}