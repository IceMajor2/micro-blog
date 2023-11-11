package com.demo.blog.blogpostservice.post.exception;

import com.demo.blog.blogpostservice.category.Category;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collection;

import static com.demo.blog.blogpostservice.post.exception.PostExceptionMessage.ALREADY_CATEGORIZED_AS;

@ResponseStatus(HttpStatus.CONFLICT)
public class PostAlreadyCategorizedException extends RuntimeException {

    public PostAlreadyCategorizedException(Collection<Category> categories) {
        super(ALREADY_CATEGORIZED_AS.getMessage()
                .formatted(String
                        .join(", ", categories.stream()
                                .map(Category::getName)
                                .toList()
                        )
                )
        );
    }
}
