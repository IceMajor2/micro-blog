package com.demo.blog.blogpostservice.author.command;

import com.demo.blog.blogpostservice.command.CommandCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AuthorCommandCode implements CommandCode {

    GET_AUTHOR("A_GET_ID");

    final String code;

    @Override
    public String getCode() {
        return this.code;
    }
}
