package com.demo.blog.blogpostservice.category.exception;

public enum CategoryExceptionMessage {

    NULL_NAME_MSG("Category name was null"),
    NULL_ID_MSG("Category ID was null"),
    NULL_REQUEST_MSG("Request was null"),
    NULL_OLD_CATEGORY_MSG("Category specified to be replaced was null");

    final String message;

    CategoryExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
