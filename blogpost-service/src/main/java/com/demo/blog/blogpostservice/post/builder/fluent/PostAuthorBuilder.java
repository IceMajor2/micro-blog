package com.demo.blog.blogpostservice.post.builder.fluent;

public interface PostAuthorBuilder {

    PostPublishedOnBuilder writtenBy(long authorId);
}
