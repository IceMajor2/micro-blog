package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.command.Command;
import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetAllPostsWrittenByCommand implements Command<List<Post>> {

    private final PostRepository postRepository;
    private final Long authorId;

    @Override
    public List<Post> execute() {
        return null;
    }
}
