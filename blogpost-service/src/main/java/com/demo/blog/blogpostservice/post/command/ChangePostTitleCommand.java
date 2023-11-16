package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.command.Command;
import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostRepository;
import com.demo.blog.blogpostservice.post.exception.PostExceptionMessage;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class ChangePostTitleCommand implements Command {

    private final PostRepository postRepository;
    private final Post post;
    private final String newTitle;

    @Override
    public Post execute() {
        Objects.requireNonNull(post, PostExceptionMessage.NULL_POST_MSG.getMessage());
        Objects.requireNonNull(newTitle, PostExceptionMessage.TITLE_BLANK_MSG.getMessage());
        post.setTitle(newTitle);
        return postRepository.save(post);
    }
}
