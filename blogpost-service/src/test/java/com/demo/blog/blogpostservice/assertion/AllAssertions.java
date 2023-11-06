package com.demo.blog.blogpostservice.assertion;

import com.demo.blog.blogpostservice.category.CategoryRepository;
import com.demo.blog.blogpostservice.category.assertion.CategoryAssert;
import com.demo.blog.blogpostservice.category.assertion.CategoryRepositoryAssert;
import com.demo.blog.blogpostservice.category.assertion.CategoryRestAssert;
import com.demo.blog.blogpostservice.category.dto.CategoryResponse;
import com.demo.blog.blogpostservice.exception.ApiExceptionDTO;
import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.assertion.PostAssert;
import com.demo.blog.blogpostservice.post.assertion.PostListAssert;
import com.demo.blog.blogpostservice.post.assertion.PostResponseAssert;
import com.demo.blog.blogpostservice.post.assertion.PostResponseListAssert;
import com.demo.blog.blogpostservice.post.dto.PostResponse;
import jakarta.validation.ConstraintViolation;
import org.assertj.core.api.Assertions;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public class AllAssertions extends Assertions {

    public static CategoryAssert assertThat(CategoryResponse actual) {
        return CategoryAssert.assertThat(actual);
    }

    public static CategoryRestAssert assertThat(ResponseEntity<CategoryResponse> actual) {
        return CategoryRestAssert.assertThat(actual);
    }

    public static CategoryRestAssert.CategoryIterableRestAssert assertThatCategories(ResponseEntity<Iterable<CategoryResponse>> actual) {
        return CategoryRestAssert.CategoryIterableRestAssert.assertThat(actual);
    }

    public static CategoryRepositoryAssert assertThat(CategoryRepository actual) {
        return CategoryRepositoryAssert.assertThat(actual);
    }

    public static PostAssert assertThat(Post actual) {
        return PostAssert.assertThat(actual);
    }

    public static PostListAssert assertThatPosts(List<Post> actual) {
        return PostListAssert.assertThat(actual);
    }

    public static PostResponseAssert assertThat(PostResponse actual) {
        return PostResponseAssert.assertThat(actual);
    }

    public static PostResponseListAssert assertThatPostResponses(List<PostResponse> actual) {
        return PostResponseListAssert.assertThat(actual);
    }

    public static HttpResponseAssert assertThatResponse(ResponseEntity<?> actual) {
        return HttpResponseAssert.assertThat(actual);
    }

    public static RestExceptionAssert assertThatException(ResponseEntity<ApiExceptionDTO> actual) {
        return RestExceptionAssert.assertThatException(actual);
    }

    public static JakartaValidationAssert assertThat(Set<ConstraintViolation<Object>> actual) {
        return JakartaValidationAssert.assertThat(actual);
    }
}
