package com.demo.blog.postservice.postcategory;

import com.demo.blog.postservice.category.Category;
import com.demo.blog.postservice.post.Post;
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
