package com.demo.blog.blogpostservice.post;

import org.junit.jupiter.api.Test;

import static com.demo.blog.blogpostservice.assertion.AllAssertions.assertThat;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.CONTAINERS_CATEGORY;
import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.SPRING_CATEGORY;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.DOCKER_POST;
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
        assertThat(post.getCategories())
                .map(postCategory -> postCategory.getCategoryId().getId())
                .contains(SPRING_CATEGORY.getId());
    }

    @Test
    void shouldAddCategory() {
        // arrange
        Post post = Post.PostBuilder.post(SPRING_POST)
                .clearCategories()
                .build();

        // act & assert
        assertThat(post.addCategory(SPRING_CATEGORY)).isTrue();
        assertThat(post.getCategories())
                .map(postCategory -> postCategory.getCategoryId().getId())
                .contains(SPRING_CATEGORY.getId());
    }

    @Test
    void shouldDoNothingOnDeletingNonAttachedCategory() {
        // arrange
        Post post = Post.PostBuilder.post(DOCKER_POST)
                .clearCategories()
                .build();

        // act & assert
        assertThat(post.deleteCategory(CONTAINERS_CATEGORY)).isFalse();
        assertThat(post.getCategories())
                .map(postCategory -> postCategory.getCategoryId().getId())
                .doesNotContain(CONTAINERS_CATEGORY.getId());
    }

    @Test
    void shouldDeleteCategory() {
        // arrange
        Post post = Post.PostBuilder.post(SPRING_POST)
                .setCategories(CONTAINERS_CATEGORY)
                .build();

        // act & assert
        assertThat(post.deleteCategory(CONTAINERS_CATEGORY)).isTrue();
        assertThat(post.getCategories())
                .map(postCategory -> postCategory.getCategoryId().getId())
                .doesNotContain(CONTAINERS_CATEGORY.getId());
    }
}