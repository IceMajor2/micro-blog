package com.demo.blog.blogpostservice.author.command;

import com.demo.blog.blogpostservice.author.AuthorRepository;
import com.demo.blog.blogpostservice.command.Command;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAuthorByIdCommand implements Command {

    private final AuthorRepository authorRepository;
    private final Long id;

    @Override
    public Object execute() {
        return null;
    }
}
