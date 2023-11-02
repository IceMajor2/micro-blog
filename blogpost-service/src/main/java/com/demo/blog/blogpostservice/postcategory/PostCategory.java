package com.demo.blog.blogpostservice.postcategory;

import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.category.Category;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Table;

@Table("post_category")
@Data
public class PostCategory {

    @Id
    private Long id;
    private AggregateReference<Category, Long> categoryId;
    private AggregateReference<Post, Long> postId;
}