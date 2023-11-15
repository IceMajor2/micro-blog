package com.demo.blog.blogpostservice.postcategory.command;

import com.demo.blog.blogpostservice.command.CommandCode;

public enum PostCategoryCommandCode implements CommandCode {

    ADD_CATEGORIES_TO_POST("PC_ADD_CAT_TO_POST"),
    DELETE_CATEGORIES_FROM_POST("PC_DEL_CAT_FROM_POST");

    final String code;

    PostCategoryCommandCode(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }
}
