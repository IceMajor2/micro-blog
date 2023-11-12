package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.command.Command;
import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostRepository;
import com.demo.blog.blogpostservice.post.exception.PostExceptionMessage;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class ReplacePostBodyCommand implements Command {

    private final PostRepository repository;
    private final Post post;
    private final String newBody;

    @Override
    public Post execute() {
        Objects.requireNonNull(post, PostExceptionMessage.NULL_POST_MSG.getMessage());
        Objects.requireNonNull(newBody, PostExceptionMessage.BODY_BLANK_MSG.getMessage());
        post.setBody(newBody);
        return repository.save(post);
    }
}
