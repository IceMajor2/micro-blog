package com.demo.blog.blogpostservice.category.command;

import com.demo.blog.blogpostservice.command.CommandCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CategoryCommandCode implements CommandCode {

    GET_CATEGORY_BY_NAME("C_GET_BY_NAME"),
    GET_CATEGORY_BY_ID("C_GET_BY_ID"),
    GET_CATEGORIES_SORTED_BY_NAME("CS_GET_SORTED_BY_NAME"),
    ADD_CATEGORY("C_ADD"),
    REPLACE_CATEGORY("C_REPLACE"),
    DELETE_CATEGORY("C_DELETE");

    final String code;

    @Override
    public String getCode() {
        return this.code;
    }
}
