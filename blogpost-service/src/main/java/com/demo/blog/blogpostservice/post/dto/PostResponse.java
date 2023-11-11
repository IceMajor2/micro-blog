package com.demo.blog.blogpostservice.post.dto;

import com.demo.blog.blogpostservice.author.Author;
import com.demo.blog.blogpostservice.author.dto.AuthorResponse;
import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.category.dto.CategoryResponse;
import com.demo.blog.blogpostservice.json.View;
import com.demo.blog.blogpostservice.post.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.util.Streamable;

import java.util.List;

public record PostResponse(
        @JsonIgnore
        Long id,
        @JsonView(View.PostCategory.class)
        String title,
        @JsonView(View.PostCategory.class)
        AuthorResponse author,
        @JsonView(View.PostCategory.class)
        List<CategoryResponse> categories,
        @JsonView(View.PostCategory.class)
        String publishedOn,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonView(View.PostCategory.class)
        String updatedOn,
        String body
) {
    public PostResponse(Post post, Author author, Iterable<Category> categories) {
        this(
                post.getId(),
                post.getTitle(),
                new AuthorResponse(author),
                Streamable.of(categories).map(CategoryResponse::new).toList(),
                post.getPublishedOn().toString(),
                post.getUpdatedOn() == null ? null : post.getUpdatedOn().toString(),
                post.getBody()
        );
    }
}
