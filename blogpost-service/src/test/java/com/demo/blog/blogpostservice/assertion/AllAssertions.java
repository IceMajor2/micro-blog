package com.demo.blog.blogpostservice.assertion;

import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.category.CategoryRepository;
import com.demo.blog.blogpostservice.category.assertion.CategoryAssert;
import com.demo.blog.blogpostservice.category.assertion.repository.CategoryRepositoryAssert;
import com.demo.blog.blogpostservice.exception.ApiExceptionDTO;
import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.assertion.PostAssert;
import com.demo.blog.blogpostservice.post.assertion.PostListAssert;
import jakarta.validation.ConstraintViolation;
import org.assertj.core.api.Assertions;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public class AllAssertions extends Assertions {

    // No generics
    public static CategoryAssert assertThat(Category actual) {
        return CategoryAssert.assertThat(actual);
    }

    public static CategoryRepositoryAssert assertThat(CategoryRepository actual) {
        return CategoryRepositoryAssert.assertThat(actual);
    }

    public static PostAssert assertThat(Post actual) {
        return PostAssert.assertThat(actual);
    }

    // Generics + SINGULAR

    public static HttpResponseAssert assertThatResponse(ResponseEntity<?> actual) {
        return HttpResponseAssert.assertThat(actual);
    }

    public static RestExceptionAssert assertThatException(ResponseEntity<ApiExceptionDTO> actual) {
        return RestExceptionAssert.assertThatException(actual);
    }

    // Generics + PLURAL

    public static PostListAssert assertThatPosts(List<Post> actual) {
        return PostListAssert.assertThat(actual);
    }

    public static JakartaValidationAssert assertThat(Set<ConstraintViolation<Object>> actual) {
        return JakartaValidationAssert.assertThat(actual);
    }
}
