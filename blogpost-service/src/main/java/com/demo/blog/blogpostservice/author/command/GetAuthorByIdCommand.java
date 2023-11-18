package com.demo.blog.blogpostservice.author.command;

import com.demo.blog.blogpostservice.author.Author;
import com.demo.blog.blogpostservice.author.AuthorRepository;
import com.demo.blog.blogpostservice.command.Command;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAuthorByIdCommand implements Command<Author> {

    private final AuthorRepository authorRepository;
    private final Long id;

    @Override
    public Author execute() {
        return null;
    }
}
