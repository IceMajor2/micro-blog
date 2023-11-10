package com.demo.blog.blogpostservice.author;

import com.demo.blog.blogpostservice.post.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class Author {

    @Id
    private Long id;
    private String username;
    private String email;
    private Set<Post> posts = new HashSet<>();
}