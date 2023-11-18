package com.demo.blog.blogpostservice.author.datasupply;

import com.demo.blog.blogpostservice.author.Author;

import static com.demo.blog.blogpostservice.datasupply.Constants.*;

public class AuthorDataSupply {

    public static final Author ANY_AUTHOR = new Author(1L, ANY_STRING, ANY_EMAIL, null);
    public static final Author JOHN_SMITH = new Author(2L, "John Smith", "smith@mail.com", null);
}
