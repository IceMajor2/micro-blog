package com.demo.blog.blogpostservice.author.exception;

import com.demo.blog.blogpostservice.exception.ExceptionMessage;

public enum AuthorExceptionMessage implements ExceptionMessage {

    NULL_AUTHOR_MSG("Author was null");

    private final String message;

    AuthorExceptionMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
