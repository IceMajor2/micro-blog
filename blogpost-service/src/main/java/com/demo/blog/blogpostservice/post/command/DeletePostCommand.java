package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.command.Command;
import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostRepository;
import com.demo.blog.blogpostservice.post.exception.PostExceptionMessage;
import com.demo.blog.blogpostservice.post.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class DeletePostCommand implements Command<Post> {

    private final PostRepository postRepository;
    private final Long postId;

    @Override
    public Post execute() {
        Objects.requireNonNull(postId, PostExceptionMessage.NULL_ID_MSG.getMessage());
        Post delete = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
        postRepository.deleteById(postId);
        return delete;
    }
}
