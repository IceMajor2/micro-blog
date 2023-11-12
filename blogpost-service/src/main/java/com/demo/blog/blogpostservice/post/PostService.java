package com.demo.blog.blogpostservice.post;

import com.demo.blog.blogpostservice.author.Author;
import com.demo.blog.blogpostservice.post.dto.PostBodyRequest;
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

    PostResponse addCategory(PostCategoryRequest request);

    PostResponse replaceBody(PostBodyRequest request);
}
