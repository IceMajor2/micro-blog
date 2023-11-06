package com.demo.blog.blogpostservice.command;

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

    public Command create(CommandCode commandCode, Object... params) {
        switch (commandCode) {
            case PostCommandCode.GET_ALL_POSTS:
                return new GetAllPostsCommand(postRepository);
            case PostCommandCode.GET_POST:
                return new GetPostCommand(postRepository, (Long) params[0]);
            default:
                return null;
        }
    }
}
