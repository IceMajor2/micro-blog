package com.demo.blog.blogpostservice.category.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.demo.blog.blogpostservice.category.exception.CategoryExceptionMessage.*;

@ResponseStatus(HttpStatus.CONFLICT)
public class CategoryAlreadyExistsException extends RuntimeException {

    public CategoryAlreadyExistsException(String categoryName) {
        super(EXISTS_MSG_T.getMessage().formatted(categoryName));
    }
}
