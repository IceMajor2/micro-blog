package com.demo.blog.blogpostservice.post.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.demo.blog.blogpostservice.post.exception.PostExceptionMessage.ID_NOT_FOUND_T;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(long id) {
        super(ID_NOT_FOUND_T.getMessage().formatted(id));
    }
}
