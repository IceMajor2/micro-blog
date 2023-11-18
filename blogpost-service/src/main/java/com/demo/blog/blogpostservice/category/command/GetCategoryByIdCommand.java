package com.demo.blog.blogpostservice.category.command;

import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.category.CategoryRepository;
import com.demo.blog.blogpostservice.category.exception.CategoryExceptionMessage;
import com.demo.blog.blogpostservice.category.exception.CategoryNotFoundException;
import com.demo.blog.blogpostservice.command.Command;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class GetCategoryByIdCommand implements Command<Category> {

    private final CategoryRepository categoryRepository;
    private final Long categoryId;

    @Override
    public Category execute() {
        Objects.requireNonNull(categoryId, CategoryExceptionMessage.NULL_ID_MSG.getMessage());
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }
}
