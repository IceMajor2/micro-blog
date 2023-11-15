package com.demo.blog.blogpostservice.post;

import com.demo.blog.blogpostservice.author.Author;
import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.post.builder.*;
import com.demo.blog.blogpostservice.post.builder.fluent.*;
import com.demo.blog.blogpostservice.post.dto.PostRequest;
import com.demo.blog.blogpostservice.postcategory.PostCategory;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Consumer;

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

    public boolean addCategory(Category category) {
        final PostCategory postCategory = new PostCategory();
        postCategory.setCategoryId(AggregateReference.to(category.getId()));
        postCategory.setPostId(AggregateReference.to(this.id));
        return categories.add(postCategory);
    }

    public boolean deleteCategory(Category category) {
        final PostCategory postCategory = new PostCategory();
        postCategory.setCategoryId(AggregateReference.to(category.getId()));
        postCategory.setPostId(AggregateReference.to(this.id));
        return categories.remove(postCategory);
    }

    // TODO: write 'remove' methods

    private static class PostAllArgsBuilder implements PostAllFieldsBuilder {

        private PostFluentBuilder fluentBuilder;

        private PostAllArgsBuilder(PostRequest request) {
            this.fluentBuilder = (PostFluentBuilder) PostFluentBuilder.post()
                    .withTitle(new String(request.title()))
                    .withBody(new String(request.body()));
        }

        private PostAllArgsBuilder(Post post) {
            this.fluentBuilder = (PostFluentBuilder) PostFluentBuilder.post()
                    .withTitle(new String(post.title))
                    .withBody(new String(post.body))
                    .writtenBy(post.author.getId().longValue())
                    .published().on(LocalDateTime.of(post.publishedOn.toLocalDate(), post.publishedOn.toLocalTime()))
                    .withId(post.getId().longValue())
                    .updated().at(deepCloneUpdatedOn(post.updatedOn))
                    .categories().setCategories(deepCloneCategories(post.getCategories()));
        }

        @Override
        public PostAllFieldsBuilder withId(long postId) {
            this.fluentBuilder = (PostFluentBuilder) fluentBuilder.withId(postId);
            return this;
        }

        @Override
        public PostAllFieldsBuilder withTitle(String title) {
            this.fluentBuilder = (PostFluentBuilder) fluentBuilder.withTitle(title);
            return this;
        }

        @Override
        public PostAllFieldsBuilder withBody(String body) {
            this.fluentBuilder = (PostFluentBuilder) fluentBuilder.withBody(body);
            return this;
        }

        @Override
        public PostAllFieldsBuilder writtenBy(long authorId) {
            this.fluentBuilder = (PostFluentBuilder) fluentBuilder.writtenBy(authorId);
            return this;
        }

        @Override
        public PostAllFieldsBuilder published(LocalDateTime publishedOn) {
            this.fluentBuilder = (PostFluentBuilder) fluentBuilder.published().on(publishedOn);
            return this;
        }

        @Override
        public PostAllFieldsBuilder updated(LocalDateTime updatedOn) {
            this.fluentBuilder = (PostFluentBuilder) fluentBuilder.updated().at(updatedOn);
            return this;
        }

        @Override
        public PostAllFieldsBuilder addCategories(Category... categories) {
            this.fluentBuilder = (PostFluentBuilder) fluentBuilder.addCategories(categories);
            return this;
        }

        @Override
        public PostAllFieldsBuilder addCategories(Collection<Category> categories) {
            addCategories(categories.toArray(new Category[0]));
            return this;
        }

        @Override
        public PostAllFieldsBuilder setCategories(Category... categories) {
            this.fluentBuilder = (PostFluentBuilder) fluentBuilder.setCategories(categories);
            return this;
        }

        @Override
        public PostAllFieldsBuilder setCategories(Collection<Category> categories) {
            setCategories(categories.toArray(new Category[0]));
            return this;
        }

        @Override
        public PostAllFieldsBuilder clearCategories() {
            this.fluentBuilder = (PostFluentBuilder) fluentBuilder.clearCategories();
            return this;
        }

        @Override
        public Post build() {
            return this.fluentBuilder.build();
        }

        private Set<PostCategory> deepCloneCategories(Set<PostCategory> categories) {
            Set<PostCategory> clone = new HashSet<>();
            categories.forEach(postCategory -> {
                Long id = postCategory.getId() != null ? postCategory.getId() + 0 : null;
                Long categoryId = postCategory.getCategoryId() != null ? postCategory.getCategoryId().getId() + 0 : null;
                Long postId = postCategory.getPostId() != null ? postCategory.getPostId().getId() + 0 : null;
                final PostCategory postCategoryClone = new PostCategory(id, categoryId, postId);
                clone.add(postCategoryClone);
            });
            return clone;
        }

        private LocalDateTime deepCloneUpdatedOn(LocalDateTime updatedOn) {
            return updatedOn != null ? updatedOn.minus(0, ChronoUnit.HOURS) : null;
        }
    }

    public static class PostFluentBuilder implements PostTitleBuilder, PostBodyBuilder, PostAuthorBuilder,
            PostPublishedOnBuilder, PostOptionalFieldsBuilder, PostPublishedOnOptions, PostUpdatedOnOptions,
            PostCategoryOptions {

        private List<Consumer<Post>> operations;

        public static PostTitleBuilder post() {
            return new PostFluentBuilder();
        }

        public static PostAllFieldsBuilder post(PostRequest request) {
            return new PostAllArgsBuilder(request);
        }

        public static PostAllFieldsBuilder post(Post post) {
            return new PostAllArgsBuilder(post);
        }

        private PostFluentBuilder() {
            this.operations = new ArrayList<>();
        }

        @Override
        public PostBodyBuilder withTitle(String title) {
            Objects.requireNonNull(title);
            this.operations.add(post -> post.title = title);
            return this;
        }

        @Override
        public PostAuthorBuilder withBody(String body) {
            Objects.requireNonNull(body);
            this.operations.add(post -> post.body = body);
            return this;
        }

        @Override
        public PostPublishedOnBuilder writtenBy(long authorId) {
            this.operations.add(post -> post.author = AggregateReference.to(authorId));
            return this;
        }

        @Override
        public PostPublishedOnOptions published() {
            return this;
        }

        @Override
        public PostOptionalFieldsBuilder withId(long postId) {
            this.operations.add(post -> post.id = postId);
            return this;
        }

        @Override
        public PostOptionalFieldsBuilder addCategories(Category... categories) {
            Objects.requireNonNull(categories);
            Arrays.stream(categories).forEach(category -> this.operations.add(post -> post.addCategory(category)));
            return this;
        }

        @Override
        public PostOptionalFieldsBuilder setCategories(Category... categories) {
            Objects.requireNonNull(categories);
            clearCategories();
            addCategories(categories);
            return this;
        }

        @Override
        public PostOptionalFieldsBuilder setCategories(Set<PostCategory> categories) {
            Objects.requireNonNull(categories);
            this.operations.add(post -> post.categories = categories);
            return this;
        }

        @Override
        public PostOptionalFieldsBuilder clearCategories() {
            this.operations.add(post -> post.categories.clear());
            return this;
        }

        @Override
        public PostCategoryOptions categories() {
            return this;
        }

        @Override
        public PostUpdatedOnOptions updated() {
            return this;
        }

        @Override
        public Post build() {
            Post post = new Post();
            operations.forEach(operation -> operation.accept(post));
            return post;
        }

        @Override
        public PostOptionalFieldsBuilder on(LocalDateTime timestamp) {
            Objects.requireNonNull(timestamp);
            this.operations.add(post -> post.publishedOn = timestamp);
            return this;
        }

        @Override
        public PostOptionalFieldsBuilder now() {
            this.operations.add(post -> post.publishedOn = LocalDateTime.now());
            return this;
        }

        @Override
        public PostOptionalFieldsBuilder fifteenMinsAgo() {
            this.operations.add(post -> post.publishedOn = LocalDateTime.now().minus(15, ChronoUnit.MINUTES));
            return this;
        }

        @Override
        public PostOptionalFieldsBuilder thirtyMinsAgo() {
            this.operations.add(post -> post.publishedOn = LocalDateTime.now().minus(30, ChronoUnit.MINUTES));
            return this;
        }

        @Override
        public PostOptionalFieldsBuilder hourAgo() {
            this.operations.add(post -> post.publishedOn = LocalDateTime.now().minus(1, ChronoUnit.HOURS));
            return this;
        }

        @Override
        public PostOptionalFieldsBuilder at(LocalDateTime timestamp) {
            this.operations.add(post -> post.updatedOn = timestamp);
            return this;
        }

        @Override
        public PostOptionalFieldsBuilder justNow() {
            this.operations.add(post -> post.updatedOn = LocalDateTime.now());
            return this;
        }
    }
}
