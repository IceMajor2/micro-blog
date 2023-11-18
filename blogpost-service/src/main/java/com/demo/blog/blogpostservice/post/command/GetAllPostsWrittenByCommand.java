package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.author.Author;
import com.demo.blog.blogpostservice.author.exception.AuthorExceptionMessage;
import com.demo.blog.blogpostservice.command.Command;
import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Streamable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GetAllPostsWrittenByCommand implements Command<List<Post>> {

    private final PostRepository postRepository;
    private final Author author;

    @Override
    public List<Post> execute() {
        Objects.requireNonNull(author, AuthorExceptionMessage.NULL_AUTHOR_MSG.getMessage());
        return Streamable.of(postRepository.findByAuthorOrderByPublishedOnDesc(author.getId()))
                .stream()
                .collect(Collectors.toList());
    }
}
