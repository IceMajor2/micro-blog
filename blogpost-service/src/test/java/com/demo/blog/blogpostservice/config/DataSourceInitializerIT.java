package com.demo.blog.blogpostservice.config;

import com.demo.blog.blogpostservice.author.Author;
import com.demo.blog.blogpostservice.author.AuthorRepository;
import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.category.CategoryBuilder;
import com.demo.blog.blogpostservice.category.CategoryRepository;
import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostRepository;
import com.demo.blog.blogpostservice.postcategory.PostCategory;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Streamable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collection;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
@TestClassOrder(ClassOrderer.Random.class)
public class DataSourceInitializerIT extends BaseIntegrationTest {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private AuthorRepository authorRepository;

    private static final Set<Category> EXPECTED_CATEGORIES = Set.of(
            new CategoryBuilder().withId(1L).withName("Spring").build(),
            new CategoryBuilder().withId(2L).withName("Java").build(),
            new CategoryBuilder().withId(3L).withName("Testing").build(),
            new CategoryBuilder().withId(4L).withName("Software Engineering").build(),
            new CategoryBuilder().withId(5L).withName("Programming languages").build(),
            new CategoryBuilder().withId(6L).withName("Learning").build()
    );

    private static final Set<Post> EXPECTED_POSTS = Set.of(
            Post.PostBuilder.post()
                    .withTitle("Java and C# comparison")
                    .withBody("Some people would argue that Java and C# are very alike...")
                    .writtenBy(2)
                    .published().now()
                    .withId(1L)
                    .build(),
            Post.PostBuilder.post()
                    .withTitle("Testing and why you should do it")
                    .withBody("Nowadays, testing is a sine qua non condition of building software")
                    .writtenBy(1)
                    .published().now()
                    .withId(2L)
                    .build(),
            Post.PostBuilder.post()
                    .withTitle("Process is more important than goals")
                    .withBody("This entry will be a little bit more different than the others...")
                    .writtenBy(1)
                    .published().now()
                    .withId(3L)
                    .build()
    );

    private static final Set<Author> EXPECTED_AUTHORS = Set.of(
            new Author(1L, "Andrew Rockman", "rockman@mail.com", null),
            new Author(2L, "Eddie Watkins", "accountED@hotmail.com", null)
    );

    private static final Set<PostCategory> EXPECTED_POST_CATEGORIES = Set.of(
            new PostCategory(1L, 5L, 1L), new PostCategory(2L, 2L, 1L), new PostCategory(3L, 3L, 2L),
            new PostCategory(4L, 4L, 2L), new PostCategory(5L, 6L, 3L)
    );

    @Test
    void dataSqlShouldLoadCategories() {
        Iterable<Category> actualCategories = categoryRepository.findAll();
        assertThat(actualCategories)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "name")
                .containsAll(EXPECTED_CATEGORIES);
    }

    @Test
    void dataSqlShouldLoadPosts() {
        Iterable<Post> actualPosts = postRepository.findAll();
        assertThat(actualPosts)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "title", "body", "author")
                .containsAll(EXPECTED_POSTS);
    }

    @Test
    void dataSqlShouldLoadAuthors() {
        Iterable<Author> actualAuthors = authorRepository.findAll();
        assertThat(actualAuthors)
                .usingRecursiveFieldByFieldElementComparatorOnFields("id", "username", "email")
                .containsAll(EXPECTED_AUTHORS);
    }

    @Test
    void dataSqlShouldLoadPostCategories() {
        Iterable<Post> posts = postRepository.findAll();
        Set<PostCategory> postCategories = Streamable.of(posts)
                .map(Post::getCategories)
                .flatMap(Collection::stream)
                .toSet();

        assertThat(postCategories).containsExactlyInAnyOrderElementsOf(EXPECTED_POST_CATEGORIES);
    }
}
