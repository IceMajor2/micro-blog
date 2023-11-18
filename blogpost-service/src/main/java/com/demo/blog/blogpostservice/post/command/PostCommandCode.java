package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.command.CommandCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PostCommandCode implements CommandCode {

    GET_ALL_POSTS("P_GET_ALL"),
    GET_POST_BY_ID("P_GET_BY_ID"),
    GET_POST_CATEGORIES_SORTED_BY_NAME("P_GET_CS_SORTED_BY_NAME"),
    ADD_POST("P_ADD"),
    REPLACE_POST_BODY("P_REPLACE_BODY"),
    CHANGE_POST_TITLE("P_CHANGE_TITLE"),
    DELETE_POST("P_DELETE"),
    GET_ALL_POSTS_OF_AUTHOR("P_GET_ALL_OF_AUTHOR");

    final String code;

    @Override
    public String getCode() {
        return this.code;
    }
}
