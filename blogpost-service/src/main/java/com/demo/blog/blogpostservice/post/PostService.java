package com.demo.blog.blogpostservice.post;

import com.demo.blog.blogpostservice.author.Author;
import com.demo.blog.blogpostservice.post.dto.PostChangeTitleRequest;
import com.demo.blog.blogpostservice.post.dto.PostReplaceBodyRequest;
import com.demo.blog.blogpostservice.post.dto.PostRequest;
import com.demo.blog.blogpostservice.post.dto.PostResponse;
import com.demo.blog.blogpostservice.postcategory.dto.PostCategoryRequest;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface PostService {

    List<PostResponse> getAllOrderedByPublishedDateDesc();

    PostResponse getById(Long id);

    PostResponse add(PostRequest request, Author author);

    void delete(Long id);

    PostResponse addCategory(PostCategoryRequest request);

    PostResponse deleteCategory(PostCategoryRequest request);

    PostResponse replaceBody(Long postId, PostReplaceBodyRequest request);

    PostResponse changeTitle(Long postId, PostChangeTitleRequest request);
}
