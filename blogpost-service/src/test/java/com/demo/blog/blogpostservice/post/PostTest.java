package com.demo.blog.blogpostservice.post;

import org.junit.jupiter.api.Test;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.SPRING_CATEGORY;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.SPRING_POST;

class PostTest {

    @Test
    void shouldNotAddCategoryThatAlreadyExists() {
        // arrange
        Post post = Post.PostBuilder.post(SPRING_POST)
                .setCategories(SPRING_CATEGORY)
                .build();

        // act & assert
        assertThat(post.addCategory(SPRING_CATEGORY)).isFalse();
    }

    @Test
    void shouldAddCategory() {
        // arrange
        Post post = Post.PostBuilder.post(SPRING_POST)
                .clearCategories()
                .build();

        // act & assert
        assertThat(post.addCategory(SPRING_CATEGORY)).isTrue();
    }

    @Test
    void shouldDeleteCategory() {

    }
}