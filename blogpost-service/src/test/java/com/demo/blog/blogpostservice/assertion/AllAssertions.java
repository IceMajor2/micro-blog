package com.demo.blog.blogpostservice.assertion;

import com.demo.blog.blogpostservice.category.CategoryRepository;
import com.demo.blog.blogpostservice.category.assertion.dto.CategoryResponseAssert;
import com.demo.blog.blogpostservice.category.assertion.repository.CategoryRepositoryAssert;
import com.demo.blog.blogpostservice.category.assertion.rest.CategoryRestAssert;
import com.demo.blog.blogpostservice.category.assertion.rest.CategoryRestListAssert;
import com.demo.blog.blogpostservice.category.dto.CategoryResponse;
import com.demo.blog.blogpostservice.exception.ApiExceptionDTO;
import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.assertion.domain.PostAssert;
import com.demo.blog.blogpostservice.post.assertion.domain.PostListAssert;
import com.demo.blog.blogpostservice.post.assertion.dto.PostResponseAssert;
import com.demo.blog.blogpostservice.post.assertion.dto.PostResponseListAssert;
import com.demo.blog.blogpostservice.post.dto.PostResponse;
import jakarta.validation.ConstraintViolation;
import org.assertj.core.api.Assertions;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public class AllAssertions extends Assertions {

    // No generics
    public static CategoryResponseAssert assertThat(CategoryResponse actual) {
        return CategoryResponseAssert.assertThat(actual);
    }

    public static CategoryRepositoryAssert assertThat(CategoryRepository actual) {
        return CategoryRepositoryAssert.assertThat(actual);
    }

    public static PostAssert assertThat(Post actual) {
        return PostAssert.assertThat(actual);
    }

    public static PostResponseAssert assertThat(PostResponse actual) {
        return PostResponseAssert.assertThat(actual);
    }

    // Generics + SINGULAR

    public static CategoryRestAssert assertThatCategoryRestResponse(ResponseEntity<CategoryResponse> actual) {
        return CategoryRestAssert.assertThat(actual);
    }

    public static HttpResponseAssert assertThatResponse(ResponseEntity<?> actual) {
        return HttpResponseAssert.assertThat(actual);
    }

    public static RestExceptionAssert assertThatException(ResponseEntity<ApiExceptionDTO> actual) {
        return RestExceptionAssert.assertThatException(actual);
    }

    // Generics + PLURAL

    public static CategoryRestListAssert assertThatCategoriesRestResponse(ResponseEntity<List<CategoryResponse>> actual) {
        return CategoryRestListAssert.assertThat(actual);
    }

    public static PostListAssert assertThatPosts(List<Post> actual) {
        return PostListAssert.assertThat(actual);
    }

    public static PostResponseListAssert assertThatPostResponses(List<PostResponse> actual) {
        return PostResponseListAssert.assertThat(actual);
    }

    public static JakartaValidationAssert assertThat(Set<ConstraintViolation<Object>> actual) {
        return JakartaValidationAssert.assertThat(actual);
    }
}
