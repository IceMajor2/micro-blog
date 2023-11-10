package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.author.Author;
import com.demo.blog.blogpostservice.command.Command;
import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostBuilder;
import com.demo.blog.blogpostservice.post.PostRepository;
import com.demo.blog.blogpostservice.post.dto.PostRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddPostCommand implements Command {

    private final PostRepository repository;
    private final PostRequest request;
    private final Author author;

    @Override
    public Post execute() {
        Post post = new PostBuilder()
                .fromRequest(request)
                .withAuthor(author.getId())
                .publishedNow()
                .build();
        return repository.save(post);
    }
}
