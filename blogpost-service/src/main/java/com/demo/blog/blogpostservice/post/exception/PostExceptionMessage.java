package com.demo.blog.blogpostservice.post.exception;

import com.demo.blog.blogpostservice.exception.ExceptionMessage;

public enum PostExceptionMessage implements ExceptionMessage {

    NULL_ID_MSG("Post ID was null"),
    NULL_REQUEST_MSG("Request was null"),
    NULL_POST_MSG("Post was null"),

    ALREADY_CATEGORIZED_AS("Post already is categorized as [%s]"),
    ID_NOT_FOUND_T("Post of '%d' ID was not found"),
    TITLE_EXISTS_MSG_T("Post with '%s' title already exists"),
    BODY_BLANK_MSG("Body must be specified"),
    TITLE_BLANK_MSG("Post title must be specified");

    private final String message;

    PostExceptionMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
