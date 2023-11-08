package com.demo.blog.blogpostservice.author.datasupply;

import com.demo.blog.blogpostservice.author.Author;

import static com.demo.blog.blogpostservice.datasupply.Constants.ANY_EMAIL;
import static com.demo.blog.blogpostservice.datasupply.Constants.ANY_STRING;

public class AuthorDataSupply {

    public static final Author ANY_AUTHOR = new Author(ANY_STRING, ANY_EMAIL);
}
