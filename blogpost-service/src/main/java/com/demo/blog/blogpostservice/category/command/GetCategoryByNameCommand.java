package com.demo.blog.blogpostservice.category.command;

import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.category.CategoryRepository;
import com.demo.blog.blogpostservice.category.exception.CategoryExceptionMessage;
import com.demo.blog.blogpostservice.category.exception.CategoryNotFoundException;
import com.demo.blog.blogpostservice.command.Command;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class GetCategoryByNameCommand implements Command<Category> {

    private final CategoryRepository categoryRepository;
    private final String categoryName;

    @Override
    public Category execute() {
        Objects.requireNonNull(categoryName, CategoryExceptionMessage.NULL_NAME_MSG.getMessage());
        return categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new CategoryNotFoundException(categoryName));
    }
}
