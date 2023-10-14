package com.demo.blog.postservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("category")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Category {

    @Id
    private Long id;
    private String name;
    private List<Post> posts;
}
