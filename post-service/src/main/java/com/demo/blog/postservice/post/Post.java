package com.demo.blog.postservice.post;

import com.demo.blog.postservice.author.Author;
import com.demo.blog.postservice.category.Category;
import com.demo.blog.postservice.postcategory.PostCategory;
import com.demo.blog.postservice.comment.Comment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class Post {

    @Id
    private Long id;
    private String title;
    private String body;
    private LocalDateTime publishedOn;
    private LocalDateTime updatedOn;
    @MappedCollection(idColumn = "post_id", keyColumn = "category_id")
    private Set<PostCategory> categories = new HashSet<>();
    private AggregateReference<Author, Long> author;
    @EqualsAndHashCode.Exclude
    private Set<Comment> comments = new HashSet<>();

    public Post(String title, String body, AggregateReference<Author, Long> author) {
        this.title = title;
        this.body = body;
        this.author = author;
        this.publishedOn = LocalDateTime.now();
    }

    void addCategory(Category category) {
        final PostCategory postCategory = new PostCategory();
        postCategory.setCategoryId(AggregateReference.to(category.getId()));
        categories.add(postCategory);
    }

    void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setPost(this);
    }

    // TODO: write 'remove' methods
}
