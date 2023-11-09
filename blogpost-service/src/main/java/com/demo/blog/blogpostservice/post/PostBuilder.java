package com.demo.blog.blogpostservice.post;

import com.demo.blog.blogpostservice.author.Author;
import com.demo.blog.blogpostservice.category.Category;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.HashSet;
import java.util.Set;

public class PostBuilder {

    private Long id;
    private String title;
    private String body;
    private LocalDateTime publishedOn;
    private LocalDateTime updatedOn;
    private Set<Category> categories = new HashSet<>();
    private AggregateReference<Author, Long> author;

    public PostBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public PostBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public PostBuilder withBody(String body) {
        this.body = body;
        return this;
    }

    public PostBuilder publishedOn(LocalDateTime publishedOn) {
        this.publishedOn = publishedOn;
        return this;
    }

    public PostBuilder publishedNow() {
        this.publishedOn = LocalDateTime.now();
        return this;
    }

    public PostBuilder publishedFifteenMinsAgo() {
        this.publishedOn = LocalDateTime.now().minus(15, ChronoUnit.MINUTES);
        return this;
    }

    public PostBuilder publishedThirtyMinsAgo() {
        this.publishedOn = LocalDateTime.now().minus(30, ChronoUnit.MINUTES);
        return this;
    }

    public PostBuilder publishedHourAgo() {
        this.publishedOn = LocalDateTime.now().minus(1, ChronoUnit.HOURS);
        return this;
    }

    public PostBuilder publishedAgo(long amount, TemporalUnit timeUnit) {
        this.publishedOn = LocalDateTime.now().minus(amount, timeUnit);
        return this;
    }

    public PostBuilder updatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public PostBuilder updatedNow() {
        this.updatedOn = LocalDateTime.now();
        return this;
    }

    public PostBuilder withCategory(Category category) {
        this.categories.add(category);
        return this;
    }

    public PostBuilder withCategories(Category... categories) {
        for (Category category : categories) {
            withCategory(category);
        }
        return this;
    }

    public PostBuilder withAuthor(long authorId) {
        this.author = AggregateReference.to(authorId);
        return this;
    }

    public Post build() {
        Post post = new Post();
        post.setId(id);
        post.setTitle(title);
        post.setAuthor(author);
        post.setBody(body);
        post.setPublishedOn(publishedOn);
        post.setUpdatedOn(updatedOn);
        for(Category category : categories) {
            post.addCategory(category);
        }
        return post;
    }
}
