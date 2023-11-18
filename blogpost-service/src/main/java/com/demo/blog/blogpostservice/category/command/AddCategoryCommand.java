package com.demo.blog.blogpostservice.category.command;

import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.category.CategoryBuilder;
import com.demo.blog.blogpostservice.category.CategoryRepository;
import com.demo.blog.blogpostservice.category.dto.CategoryRequest;
import com.demo.blog.blogpostservice.category.exception.CategoryAlreadyExistsException;
import com.demo.blog.blogpostservice.category.exception.CategoryExceptionMessage;
import com.demo.blog.blogpostservice.command.Command;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class AddCategoryCommand implements Command<Category> {

    private final CategoryRepository categoryRepository;
    private final CategoryRequest categoryRequest;

    @Override
    public Category execute() {
        Objects.requireNonNull(categoryRequest, CategoryExceptionMessage.NULL_REQUEST_MSG.getMessage());
        Category category = new CategoryBuilder()
                .fromRequest(categoryRequest)
                .build();
        if (categoryRepository.existsByName(category.getName()))
            throw new CategoryAlreadyExistsException(category.getName());
        return categoryRepository.save(category);
    }
}
