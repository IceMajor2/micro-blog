package com.demo.blog.blogpostservice.author;

import com.demo.blog.blogpostservice.author.dto.AuthorRequest;
import com.demo.blog.blogpostservice.author.dto.AuthorResponse;
import org.springframework.validation.annotation.Validated;

@Validated
public interface AuthorService {

    AuthorResponse getById(Long authorId);

    AuthorResponse add(AuthorRequest request);

    void delete(Long authorId);
}
