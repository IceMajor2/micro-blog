package com.demo.blog.postservice.category;

import com.demo.blog.postservice.post.Post;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document("category")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Category {

    @Id
    private Long id;
    @Indexed(unique = true)
    @Nonnull
    private String name;
    private Set<Post> posts = new HashSet<>();
}
