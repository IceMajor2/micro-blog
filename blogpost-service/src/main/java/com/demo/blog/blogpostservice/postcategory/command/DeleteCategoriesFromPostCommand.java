package com.demo.blog.blogpostservice.postcategory.command;

import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.category.exception.CategoryExceptionMessage;
import com.demo.blog.blogpostservice.category.exception.CategoryNotFoundException;
import com.demo.blog.blogpostservice.command.Command;
import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostRepository;
import com.demo.blog.blogpostservice.post.exception.PostExceptionMessage;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class DeleteCategoriesFromPostCommand implements Command {

    private final PostRepository postRepository;
    private final Post post;
    private final List<Category> categories;

    @Override
    public Post execute() {
        Objects.requireNonNull(post, PostExceptionMessage.NULL_POST_MSG.getMessage());
        Objects.requireNonNull(categories, CategoryExceptionMessage.NULL_CATEGORIES_MSG.getMessage());
        if (categories.isEmpty())
            throw new IllegalStateException(CategoryExceptionMessage.CATEGORIES_EMPTY_MSG.getMessage());
        List<Long> currentCategoryIds = post.getCategories().stream()
                .map(postCategory -> postCategory.getCategoryId().getId())
                .toList();
        List<Long> newCategoryIds = categories.stream()
                .map(Category::getId)
                .toList();
        if(currentCategoryIds.stream().noneMatch(newCategoryIds::contains))
            throw new CategoryNotFoundException(categories);
        categories.forEach(post::deleteCategory);
        return postRepository.save(post);
    }
}
