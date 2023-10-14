package com.demo.blog.postservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document("post")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Post {

    @Id
    private Long id;
    private String title;
    private String body;
  //  private User author;
    private Category category;
    private Date createdAt;
  //  private List<Comment> comments;
}
