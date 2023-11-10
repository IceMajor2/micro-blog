package com.demo.blog.blogpostservice.post.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.demo.blog.blogpostservice.post.exception.PostExceptionMessage.TITLE_EXISTS_MSG_T;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostAlreadyExistsException extends RuntimeException {

    public PostAlreadyExistsException(String postTitle) {
        super(TITLE_EXISTS_MSG_T.getMessage().formatted(postTitle));
    }
}
