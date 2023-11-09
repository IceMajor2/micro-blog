package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.command.CommandCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PostCommandCode implements CommandCode {

    GET_ALL_POSTS("P_GET_ALL"), GET_POST_BY_ID("P_GET_BY_ID");

    final String code;

    @Override
    public String getCode() {
        return this.code;
    }
}
