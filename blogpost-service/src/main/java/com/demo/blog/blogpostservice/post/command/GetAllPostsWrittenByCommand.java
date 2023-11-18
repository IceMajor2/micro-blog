package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.command.Command;
import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Streamable;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GetAllPostsWrittenByCommand implements Command<List<Post>> {

    private final PostRepository postRepository;
    private final Long authorId;

    @Override
    public List<Post> execute() {
        return Streamable.of(postRepository.findByAuthorOrderByPublishedOnDesc(authorId))
                .stream()
                .collect(Collectors.toList());
    }
}
