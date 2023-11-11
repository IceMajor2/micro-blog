package com.demo.blog.blogpostservice.postcategory.command;

import com.demo.blog.blogpostservice.command.CommandCode;

public enum PostCategoryCommandCode implements CommandCode {

    ADD_CATEGORIES_TO_POST("PC_ADD_CS_TO_POST");

    final String code;

    PostCategoryCommandCode(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }
}
