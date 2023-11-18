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
public class ReplaceCategoryCommand implements Command<Category> {

    private final CategoryRepository categoryRepository;
    private final Category oldCategory;
    private final CategoryRequest newCategory;

    @Override
    public Category execute() {
        // null check
        Objects.requireNonNull(oldCategory, CategoryExceptionMessage.NULL_OLD_CATEGORY_MSG.getMessage());
        Objects.requireNonNull(newCategory, CategoryExceptionMessage.NULL_REQUEST_MSG.getMessage());

        Category replacement = new CategoryBuilder()
                .withId(oldCategory.getId())
                .withName(newCategory.name())
                .build();
        if (categoryRepository.exists(replacement))
            throw new CategoryAlreadyExistsException(replacement.getName());
        return categoryRepository.save(replacement);
    }
}
