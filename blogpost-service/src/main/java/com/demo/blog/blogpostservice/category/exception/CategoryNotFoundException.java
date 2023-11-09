package com.demo.blog.blogpostservice.category.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.demo.blog.blogpostservice.category.exception.CategoryExceptionMessage.NOT_FOUND_ID_MSG_T;
import static com.demo.blog.blogpostservice.category.exception.CategoryExceptionMessage.NOT_FOUND_NAME_MSG_T;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String categoryName) {
        super(NOT_FOUND_NAME_MSG_T.getMessage().formatted(categoryName));
    }

    public CategoryNotFoundException(Long categoryId) {
        super(NOT_FOUND_ID_MSG_T.getMessage().formatted(categoryId));
    }
}
