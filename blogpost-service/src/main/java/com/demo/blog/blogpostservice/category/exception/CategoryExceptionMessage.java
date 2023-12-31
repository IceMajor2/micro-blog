package com.demo.blog.blogpostservice.category.exception;

import com.demo.blog.blogpostservice.exception.ExceptionMessage;

public enum CategoryExceptionMessage implements ExceptionMessage {

    NULL_NAME_MSG("Category name was null"),
    NULL_ID_MSG("Category ID was null"),
    NULL_REQUEST_MSG("Request was null"),
    NULL_OLD_CATEGORY_MSG("Category specified to be replaced was null"),
    NULL_CATEGORIES_MSG("Categories were null"),
    NULL_CATEGORY_MSG("Category was null"),

    EMPTY_CATEGORIES_MSG("List of categories was empty"),

    EXISTS_MSG_T("Category '%s' already exists"),
    NOT_FOUND_ID_MSG_T("Category of '%d' ID was not found"),
    NOT_FOUND_NAME_MSG_T("Category '%s' was not found"),
    NOT_FOUND_NAMES_MSG_T("Categories [%s] were not found");

    private final String message;

    CategoryExceptionMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
