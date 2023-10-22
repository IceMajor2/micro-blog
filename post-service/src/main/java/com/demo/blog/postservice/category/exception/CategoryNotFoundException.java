package com.demo.blog.postservice.category.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.StringTemplate.STR;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String categoryName) {
        super(STR."Category '\{ categoryName }' was not found");
    }

    public CategoryNotFoundException(Long categoryId) {
        super(STR."Category of '\{ categoryId }' ID was not found");
    }
}
