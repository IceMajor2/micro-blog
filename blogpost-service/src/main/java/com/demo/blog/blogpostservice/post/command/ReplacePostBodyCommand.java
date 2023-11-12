package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.command.Command;
import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReplacePostBodyCommand implements Command {

    private final PostRepository repository;
    private final Post post;
    private final String newBody;

    @Override
    public Post execute() {
        post.setBody(newBody);
        return repository.save(post);
    }
}
