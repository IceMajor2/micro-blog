package com.demo.blog.blogpostservice.author.dto;

import com.demo.blog.blogpostservice.author.Author;

public record AuthorResponse(String name) {

    public AuthorResponse(Author author) {
        this(author.getUsername());
    }
}
