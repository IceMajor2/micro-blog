package com.demo.blog.blogpostservice.post;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.SPRING_CATEGORY;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.SPRING_POST;

class PostTest {

    @Test
    void shouldNotAddCategoryThatAlreadyExists() {
        // arrange
        Post post = new PostBuilder()
                .from(SPRING_POST)
                .replacingCategories(Collections.singleton(SPRING_CATEGORY))
                .build();

        // act & assert
        assertThat(post.addCategory(SPRING_CATEGORY)).isFalse();
    }

    @Test
    void shouldAddCategory() {
        // arrange
        Post post = new PostBuilder()
                .from(SPRING_POST)
                .clearCategories()
                .build();

        // act & assert
        assertThat(post.addCategory(SPRING_CATEGORY)).isTrue();
    }
}