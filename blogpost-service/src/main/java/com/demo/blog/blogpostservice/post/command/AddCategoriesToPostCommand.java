package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.command.Command;
import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

import static com.demo.blog.blogpostservice.category.exception.CategoryExceptionMessage.NULL_CATEGORIES_MSG;
import static com.demo.blog.blogpostservice.post.exception.PostExceptionMessage.NULL_POST_MSG;

@RequiredArgsConstructor
public class AddCategoriesToPostCommand implements Command {

    private final PostRepository postRepository;
    private final Post post;
    private final List<Category> categories;

    @Override
    public Post execute() {
        Objects.requireNonNull(post, NULL_POST_MSG.getMessage());
        Objects.requireNonNull(categories, NULL_CATEGORIES_MSG.getMessage());
        categories.forEach(post::addCategory);
        return postRepository.save(post);
    }
}
