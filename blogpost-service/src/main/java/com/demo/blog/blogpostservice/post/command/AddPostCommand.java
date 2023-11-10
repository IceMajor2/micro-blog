package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.author.Author;
import com.demo.blog.blogpostservice.author.exception.AuthorExceptionMessage;
import com.demo.blog.blogpostservice.command.Command;
import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostBuilder;
import com.demo.blog.blogpostservice.post.PostRepository;
import com.demo.blog.blogpostservice.post.dto.PostRequest;
import com.demo.blog.blogpostservice.post.exception.PostAlreadyExistsException;
import com.demo.blog.blogpostservice.post.exception.PostExceptionMessage;
import lombok.RequiredArgsConstructor;

import java.util.Objects;


@RequiredArgsConstructor
public class AddPostCommand implements Command {

    private final PostRepository repository;
    private final PostRequest request;
    private final Author author;

    @Override
    public Post execute() {
        Objects.requireNonNull(request, PostExceptionMessage.NULL_REQUEST_MSG.getMessage());
        Objects.requireNonNull(author, AuthorExceptionMessage.NULL_AUTHOR_MSG.getMessage());
        if(repository.existsByTitle(request.title()))
            throw new PostAlreadyExistsException(request.title());
        Post post = new PostBuilder()
                .fromRequest(request)
                .withAuthor(author.getId())
                .publishedNow()
                .build();
        return repository.save(post);
    }
}
