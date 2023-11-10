package com.demo.blog.blogpostservice.postcategory;

import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.category.Category;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Table;

@Table("post_category")
@Data
@NoArgsConstructor
public class PostCategory {

    @Id
    private Long id;
    private AggregateReference<Category, Long> categoryId;
    private AggregateReference<Post, Long> postId;

    public PostCategory(Long id, Long categoryId, Long postId) {
        this.id = id;
        this.categoryId = AggregateReference.to(categoryId);
        this.postId = AggregateReference.to(postId);
    }
}