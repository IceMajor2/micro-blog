package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.command.Command;
import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChangePostTitleCommand implements Command {

    private final PostRepository postRepository;
    private final Post post;
    private final String newTitle;

    @Override
    public Post execute() {
        post.setTitle(newTitle);
        return postRepository.save(post);
    }
}
