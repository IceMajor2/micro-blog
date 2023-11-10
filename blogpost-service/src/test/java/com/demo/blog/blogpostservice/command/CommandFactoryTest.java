package com.demo.blog.blogpostservice.command;

import com.demo.blog.blogpostservice.author.AuthorRepository;
import com.demo.blog.blogpostservice.category.CategoryRepository;
import com.demo.blog.blogpostservice.category.command.*;
import com.demo.blog.blogpostservice.post.PostRepository;
import com.demo.blog.blogpostservice.post.command.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;

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
            Command actual = SUT.create(CategoryCommandCode.GET_CATEGORY_BY_ID);

            // assert
            assertThat(actual).isInstanceOf(GetCategoryByIdCommand.class);
        }

        @Test
        void shouldReturnGetCategoryByNameCommand() {
            // act
            Command actual = SUT.create(CategoryCommandCode.GET_CATEGORY_BY_NAME);

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
            Command actual = SUT.create(CategoryCommandCode.ADD_CATEGORY);

            // assert
            assertThat(actual).isInstanceOf(AddCategoryCommand.class);
        }

        @Test
        void shouldReturnReplaceCategoryCommand() {
            // act
            Command actual = SUT.create(CategoryCommandCode.REPLACE_CATEGORY);

            // assert
            assertThat(actual).isInstanceOf(ReplaceCategoryCommand.class);
        }

        @Test
        void shouldReturnDeleteCategoryCommand() {
            // act
            Command actual = SUT.create(CategoryCommandCode.DELETE_CATEGORY);

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
            Command actual = SUT.create(PostCommandCode.GET_POST_BY_ID);

            // assert
            assertThat(actual).isInstanceOf(GetPostByIdCommand.class);
        }

        @Test
        void shouldReturnGetPostCategoriesSortedByNameCommand() {
            // act
            Command actual = SUT.create(PostCommandCode.GET_POST_CATEGORIES_SORTED_BY_NAME);

            // assert
            assertThat(actual).isInstanceOf(GetPostCategoriesCommand.class);
        }

        @Test
        void shouldReturnAddPostCommand() {
            // act
            Command actual = SUT.create(PostCommandCode.ADD_POST);

            // assert
            assertThat(actual).isInstanceOf(AddPostCommand.class);
        }
    }
}