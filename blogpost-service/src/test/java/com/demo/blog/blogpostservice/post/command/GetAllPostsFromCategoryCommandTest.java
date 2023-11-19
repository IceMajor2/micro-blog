package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThatPosts;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.JAVA_CATEGORY;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.JAVA_POST;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.SPRING_POST;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
class GetAllPostsFromCategoryCommandTest {

    private GetAllPostsFromCategoryCommand SUT;

    @Mock
    private PostRepository postRepository;

    @Test
    void shouldReturnPostsFromCategoryInOrder() {
        // arrange
        List<Post> expectedPosts = List.of(JAVA_POST, SPRING_POST);
        SUT = new GetAllPostsFromCategoryCommand(postRepository, JAVA_CATEGORY);

        when(postRepository.findByCategoryOrderByPublishedOnDesc(JAVA_CATEGORY.getId()))
                .thenReturn(List.of(JAVA_POST, SPRING_POST));

        // act
        List<Post> actual = SUT.execute();

        // assert
        assertThatPosts(actual)
                .isSortedByNewest()
                .isEqualTo(expectedPosts);
    }
}