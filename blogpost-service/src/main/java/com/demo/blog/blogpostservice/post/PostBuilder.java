package com.demo.blog.blogpostservice.post;

import com.demo.blog.blogpostservice.author.Author;
import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.post.dto.PostRequest;
import com.demo.blog.blogpostservice.postcategory.PostCategory;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.util.Streamable;

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
    private Set<PostCategory> categories = new HashSet<>();
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

    public PostBuilder withCategory(PostCategory category) {
        this.categories.add(category);
        return this;
    }

    public PostBuilder withCategory(Category category) {
        final PostCategory postCategory = new PostCategory();
        postCategory.setCategoryId(AggregateReference.to(category.getId()));
        this.categories.add(postCategory);
        return this;
    }

    public PostBuilder withCategories(Category... categories) {
        for (Category category : categories) {
            withCategory(category);
        }
        return this;
    }

    public PostBuilder replacingCategories(Iterable<Category> categories) {
        this.categories.clear();
        withCategories(Streamable.of(categories).toList().toArray(new Category[0]));
        return this;
    }

    public PostBuilder clearCategories() {
        this.categories.clear();
        return this;
    }

    public PostBuilder withAuthor(long authorId) {
        this.author = AggregateReference.to(authorId);
        return this;
    }

    public PostBuilder from(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.body = post.getBody();
        this.publishedOn = post.getPublishedOn();
        this.updatedOn = post.getUpdatedOn();
        this.categories = post.getCategories();
        this.author = post.getAuthor();
        return this;
    }

    public PostBuilder fromRequest(PostRequest request) {
        this.title = request.title();
        this.body = request.body();
        return this;
    }

    public Post build() {
        Post post = new Post();
        post.setId(id);
        post.setTitle(title);
        post.setBody(body);
        post.setPublishedOn(publishedOn);
        post.setUpdatedOn(updatedOn);
        post.setCategories(categories);
        post.setAuthor(author);
        return post;
    }
}
