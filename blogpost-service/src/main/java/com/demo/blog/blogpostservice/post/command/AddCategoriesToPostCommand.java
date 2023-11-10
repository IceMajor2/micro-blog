package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.command.Command;
import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AddCategoriesToPostCommand implements Command {

    private final PostRepository postRepository;
    private final Post post;
    private final List<Category> categories;

    @Override
    public Post execute() {
        categories.forEach(post::addCategory);
        return postRepository.save(post);
    }
}
