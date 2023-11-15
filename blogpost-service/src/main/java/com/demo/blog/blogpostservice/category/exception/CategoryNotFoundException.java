package com.demo.blog.blogpostservice.category.exception;

import com.demo.blog.blogpostservice.category.Category;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collection;

import static com.demo.blog.blogpostservice.category.exception.CategoryExceptionMessage.*;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String categoryName) {
        super(NOT_FOUND_NAME_MSG_T.getMessage().formatted(categoryName));
    }

    public CategoryNotFoundException(Collection<Category> categories) {
        super(NOT_FOUND_NAMES_MSG_T.getMessage()
                .formatted(String
                        .join(", ", categories.stream()
                                .map(Category::getName)
                                .toList()
                        )
                )
        );
    }

    public CategoryNotFoundException(Long categoryId) {
        super(NOT_FOUND_ID_MSG_T.getMessage().formatted(categoryId));
    }
}
