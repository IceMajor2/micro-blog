package com.demo.blog.postservice.comment;

import com.demo.blog.postservice.post.Post;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.time.LocalDateTime;

// TODO: make it a microservice?
@Data
public final class Comment {

    private String username;
    private String body;
    private LocalDateTime publishedOn;
    private LocalDateTime updatedOn;

    @Transient
    private Post post;

    public Comment(String username, String body) {
        this.username = username;
        this.body = body;
        this.publishedOn = LocalDateTime.now();
    }
}
