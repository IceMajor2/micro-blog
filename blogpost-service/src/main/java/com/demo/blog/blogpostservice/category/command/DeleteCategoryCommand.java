package com.demo.blog.blogpostservice.category.command;

import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.category.CategoryRepository;
import com.demo.blog.blogpostservice.category.exception.CategoryExceptionMessage;
import com.demo.blog.blogpostservice.category.exception.CategoryNotFoundException;
import com.demo.blog.blogpostservice.command.Command;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class DeleteCategoryCommand implements Command {

    private final CategoryRepository categoryRepository;
    private final Long categoryDeleteId;

    @Override
    public Category execute() {
        Objects.requireNonNull(categoryDeleteId, CategoryExceptionMessage.NULL_ID_MSG.getMessage());
        Category toDelete = categoryRepository.findById(categoryDeleteId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryDeleteId));
        categoryRepository.deleteById(categoryDeleteId);
        return toDelete;
    }
}
