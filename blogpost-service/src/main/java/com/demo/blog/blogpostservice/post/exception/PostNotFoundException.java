package com.demo.blog.blogpostservice.post.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(long id) {
        super(STR. "Post of '\{ id }' ID was not found" );
    }
}
