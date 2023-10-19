package com.demo.blog.postservice.category.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CategoryAlreadyExistsException extends RuntimeException {

    public CategoryAlreadyExistsException(String categoryName) {
        super(STR."Category '\{ categoryName }' already exists");
    }
}
