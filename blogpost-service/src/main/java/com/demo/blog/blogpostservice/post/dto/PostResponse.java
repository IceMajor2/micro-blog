package com.demo.blog.blogpostservice.post.dto;

import com.demo.blog.blogpostservice.author.Author;
import com.demo.blog.blogpostservice.author.dto.AuthorResponse;
import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.category.dto.CategoryResponse;
import com.demo.blog.blogpostservice.post.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.util.Streamable;

import java.util.List;

public record PostResponse(
        @JsonIgnore Long id,
        String title,
        AuthorResponse author,
        List<CategoryResponse> categories,
        String publishedOn,
        @JsonInclude(JsonInclude.Include.NON_NULL) String updatedOn,
        String body
) {
    public PostResponse(Post post) {
        this(
                post.getId(),
                post.getTitle(),
                null,
                null,
                post.getPublishedOn().toString(),
                post.getUpdatedOn() == null ? null : post.getUpdatedOn().toString(),
                post.getBody()
        );
    }

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
