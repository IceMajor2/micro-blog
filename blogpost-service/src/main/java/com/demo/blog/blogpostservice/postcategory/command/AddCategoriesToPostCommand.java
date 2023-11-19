package com.demo.blog.blogpostservice.postcategory.command;

import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.category.exception.CategoryExceptionMessage;
import com.demo.blog.blogpostservice.command.Command;
import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostRepository;
import com.demo.blog.blogpostservice.post.exception.PostAlreadyCategorizedException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import static com.demo.blog.blogpostservice.category.exception.CategoryExceptionMessage.NULL_CATEGORIES_MSG;
import static com.demo.blog.blogpostservice.post.exception.PostExceptionMessage.NULL_POST_MSG;

@RequiredArgsConstructor
public class AddCategoriesToPostCommand implements Command<Post> {

    private final PostRepository postRepository;
    private final Post post;
    private final List<Category> categories;

    @Override
    public Post execute() {
        Objects.requireNonNull(post, NULL_POST_MSG.getMessage());
        Objects.requireNonNull(categories, NULL_CATEGORIES_MSG.getMessage());
        if (categories.isEmpty())
            throw new IllegalStateException(CategoryExceptionMessage.EMPTY_CATEGORIES_MSG.getMessage());
        List<Long> currentCategoryIds = post.getCategories().stream()
                .map(postCategory -> postCategory.getCategoryId().getId())
                .toList();
        List<Long> newCategoryIds = categories.stream()
                .map(Category::getId)
                .toList();
        if (new HashSet<>(currentCategoryIds).containsAll(newCategoryIds))
            throw new PostAlreadyCategorizedException(categories);
        categories.forEach(post::addCategory);
        post.setUpdatedOn(LocalDateTime.now());
        return postRepository.save(post);
    }
}
