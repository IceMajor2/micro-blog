package com.demo.blog.blogpostservice.command;

import com.demo.blog.blogpostservice.author.AuthorRepository;
import com.demo.blog.blogpostservice.author.command.AuthorCommandCode;
import com.demo.blog.blogpostservice.author.command.GetAuthorByIdCommand;
import com.demo.blog.blogpostservice.post.PostRepository;
import com.demo.blog.blogpostservice.post.command.GetAllPostsCommand;
import com.demo.blog.blogpostservice.post.command.GetPostCommand;
import com.demo.blog.blogpostservice.post.command.PostCommandCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandFactory {

    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;

    public Command create(CommandCode commandCode, Object... params) {
        switch (commandCode) {
            case PostCommandCode.GET_ALL_POSTS:
                return new GetAllPostsCommand(postRepository);
            case PostCommandCode.GET_POST:
                return new GetPostCommand(postRepository, (Long) params[0]);
            case AuthorCommandCode.GET_AUTHOR:
                return new GetAuthorByIdCommand(authorRepository, (Long) params[0]);
            default:
                return null;
        }
    }
}
