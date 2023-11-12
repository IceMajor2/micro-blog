package com.demo.blog.blogpostservice.post;

import com.demo.blog.blogpostservice.author.Author;
import com.demo.blog.blogpostservice.author.command.AuthorCommandCode;
import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.category.command.CategoryCommandCode;
import com.demo.blog.blogpostservice.category.exception.CategoryNotFoundException;
import com.demo.blog.blogpostservice.command.CommandFactory;
import com.demo.blog.blogpostservice.post.command.PostCommandCode;
import com.demo.blog.blogpostservice.post.dto.PostBodyRequest;
import com.demo.blog.blogpostservice.post.dto.PostRequest;
import com.demo.blog.blogpostservice.post.dto.PostResponse;
import com.demo.blog.blogpostservice.postcategory.command.PostCategoryCommandCode;
import com.demo.blog.blogpostservice.postcategory.dto.PostCategoryRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final CommandFactory commandFactory;

    @Override
    public PostResponse getById(Long id) {
        Post post = (Post) commandFactory
                .create(PostCommandCode.GET_POST_BY_ID, id)
                .execute();
        Author author = (Author) commandFactory
                .create(AuthorCommandCode.GET_AUTHOR, post.getAuthor().getId())
                .execute();
        List<Category> categories = (List<Category>) commandFactory
                .create(PostCommandCode.GET_POST_CATEGORIES_SORTED_BY_NAME, post.getId())
                .execute();
        return new PostResponse(post, author, categories);
    }

    @Override
    public List<PostResponse> getAllOrderedByPublishedDateDesc() {
        List<PostResponse> responses = new ArrayList<>();
        List<Post> posts = (List<Post>) commandFactory
                .create(PostCommandCode.GET_ALL_POSTS)
                .execute();
        for (Post post : posts) {
            Author author = (Author) commandFactory
                    .create(AuthorCommandCode.GET_AUTHOR, post.getAuthor().getId())
                    .execute();
            List<Category> categories = (List<Category>) commandFactory
                    .create(PostCommandCode.GET_POST_CATEGORIES_SORTED_BY_NAME, post.getId())
                    .execute();
            responses.add(new PostResponse(post, author, categories));
        }
        return responses;
    }

    @Override
    public PostResponse add(PostRequest request, Author author) {
        Post persisted = (Post) commandFactory
                .create(PostCommandCode.ADD_POST, request, author)
                .execute();
        log.info(STR. "Added post: '\{ persisted }'" );
        return new PostResponse(persisted, author, Collections.emptyList());
    }

    @Override
    public PostResponse addCategory(PostCategoryRequest request) {
        Post post = (Post) commandFactory
                .create(PostCommandCode.GET_POST_BY_ID, request.postId())
                .execute();
        List<Category> newCategories = new ArrayList<>();
        for (Long categoryId : request.categoryIds()) {
            try {
                Category category = (Category) commandFactory
                        .create(CategoryCommandCode.GET_CATEGORY_BY_ID, categoryId)
                        .execute();
                newCategories.add(category);
            } catch (CategoryNotFoundException e) {}
        }
        Post persisted = (Post) commandFactory
                .create(PostCategoryCommandCode.ADD_CATEGORIES_TO_POST, post, newCategories)
                .execute();
        Author author = (Author) commandFactory
                .create(AuthorCommandCode.GET_AUTHOR, post.getAuthor().getId())
                .execute();
        List<Category> categories = (List<Category>) commandFactory
                .create(PostCommandCode.GET_POST_CATEGORIES_SORTED_BY_NAME, post.getId())
                .execute();
        log.info(STR. "New categories have been associated with the post '\{ persisted }'" );
        return new PostResponse(persisted, author, categories);
    }

    @Override
    public PostResponse replaceBody(PostBodyRequest request) {
        Post post = (Post) commandFactory
                .create(PostCommandCode.GET_POST_BY_ID, request.postId())
                .execute();
        Post persisted = (Post) commandFactory
                .create(PostCommandCode.REPLACE_POST_BODY, post, request.body())
                .execute();
        Author author = (Author) commandFactory
                .create(AuthorCommandCode.GET_AUTHOR, post.getAuthor().getId())
                .execute();
        List<Category> categories = (List<Category>) commandFactory
                .create(PostCommandCode.GET_POST_CATEGORIES_SORTED_BY_NAME, post.getId())
                .execute();
        log.info(STR. "Post '\{ persisted }' body has been updated");
        return new PostResponse(persisted, author, categories);
    }
}
