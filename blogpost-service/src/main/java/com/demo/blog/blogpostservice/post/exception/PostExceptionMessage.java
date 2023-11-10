package com.demo.blog.blogpostservice.post.exception;

import com.demo.blog.blogpostservice.exception.ExceptionMessage;

public enum PostExceptionMessage implements ExceptionMessage {

    ID_NOT_FOUND_T("Post of '%d' ID was not found"),
    NULL_ID_MSG("Post ID was null"),
    TITLE_EXISTS_MSG_T("Post with '%s' title already exists");

    private final String message;

    PostExceptionMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
