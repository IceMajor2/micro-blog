package com.demo.blog.blogpostservice.post.exception;

public enum PostExceptionMessage {

    // TODO: use enumerated in custom exceptions
    // TODO: create an interface for this enum

    NULL_ID_MSG("Post ID was null");

    final String message;

    PostExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
