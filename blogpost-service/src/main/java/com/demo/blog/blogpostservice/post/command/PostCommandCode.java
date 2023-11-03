package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.command.CommandCode;

public enum PostCommandCode implements CommandCode {
    GET_ALL_POSTS("P_1");

    String code;

    PostCommandCode(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }
}
