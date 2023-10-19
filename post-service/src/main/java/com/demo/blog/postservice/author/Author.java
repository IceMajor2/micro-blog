package com.demo.blog.postservice.author;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public final class Author {

    @Id
    private Long id;
    private String username;
    private String email;

    public Author(String username, String email) {
        this.username = username;
        this.email = email;
    }
}


