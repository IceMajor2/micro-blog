package com.demo.blog.blogpostservice.post;

import com.demo.blog.blogpostservice.author.Author;
import com.demo.blog.blogpostservice.postcategory.PostCategory;
import com.demo.blog.blogpostservice.category.Category;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Table("post")
@NoArgsConstructor
@Data
public class Post {

    @Id
    private Long id;
    private String title;
    @ToString.Exclude
    private String body;
    private LocalDateTime publishedOn;
    private LocalDateTime updatedOn;
    @MappedCollection(idColumn = "post_id", keyColumn = "category_id")
    private Set<PostCategory> categories = new HashSet<>();
    private AggregateReference<Author, Long> author;

    public Post(String title, String body, AggregateReference<Author, Long> author) {
        this.title = title;
        this.body = body;
        this.author = author;
        this.publishedOn = LocalDateTime.now();
    }

    public boolean addCategory(Category category) {
        final PostCategory postCategory = new PostCategory();
        postCategory.setCategoryId(AggregateReference.to(category.getId()));
        return categories.add(postCategory);
    }

    // TODO: write 'remove' methods
}
