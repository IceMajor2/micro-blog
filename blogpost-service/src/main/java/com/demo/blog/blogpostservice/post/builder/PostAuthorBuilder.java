package com.demo.blog.blogpostservice.post.builder;

public interface PostAuthorBuilder {

    PostPublishedOnBuilder writtenBy(long authorId);
}
