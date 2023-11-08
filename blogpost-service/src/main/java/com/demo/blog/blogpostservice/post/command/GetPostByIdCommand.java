package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.command.Command;
import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostRepository;
import com.demo.blog.blogpostservice.post.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetPostCommand implements Command {

    private final PostRepository postRepository;
    private final Long postId;

    @Override
    public Post execute() {
        return postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
    }
}
