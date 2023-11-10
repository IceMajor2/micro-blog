package com.demo.blog.blogpostservice.post;

import com.demo.blog.blogpostservice.author.Author;
import com.demo.blog.blogpostservice.author.AuthorRepository;
import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.category.CategoryRepository;
import com.demo.blog.blogpostservice.config.BaseIntegrationTest;
import com.demo.blog.blogpostservice.exception.ApiExceptionDTO;
import com.demo.blog.blogpostservice.post.dto.PostResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThatException;
import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThatResponse;
import static com.demo.blog.blogpostservice.post.datasupply.PostConstants.*;
import static com.demo.blog.blogpostservice.util.RestRequestUtils.get;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
@TestClassOrder(ClassOrderer.Random.class)
public class PostControllerHttpGetIT extends BaseIntegrationTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Nested
    @TestMethodOrder(MethodOrderer.Random.class)
    class GetById {

        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void shouldReturnCategoryOnGetId(Long postId) {
            // arrange
            Post expectedPost = postRepository.findById(postId).get();
            Author expectedAuthor = authorRepository.findById(expectedPost.getAuthor().getId()).get();
            Iterable<Category> categories = categoryRepository.findByPostId(postId);
            PostResponse expected = new PostResponse(expectedPost, expectedAuthor, categories);

            // act
            var actual = get(API_POST_ID, PostResponse.class, postId);

            // assert
            assertThatResponse(actual)
                    .statusCodeIsOK()
                    .ignoringIdEqualTo(expected);
        }

        @ParameterizedTest
        @ValueSource(longs = {-6345, 0, 895423})
        void shouldThrowExceptionOnIdNotFound(Long noSuchPostId) {
            // act
            var actual = get(API_POST_ID, ApiExceptionDTO.class, noSuchPostId);

            // assert
            assertThatException(actual)
                    .isNotFound()
                    .withMessage(ID_NOT_FOUND_MSG_T.formatted(noSuchPostId))
                    .withPath(API_POST_SLASH + noSuchPostId);
        }
    }
}
