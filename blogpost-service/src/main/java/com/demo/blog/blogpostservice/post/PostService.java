package com.demo.blog.blogpostservice.post;

import com.demo.blog.blogpostservice.post.dto.PostResponse;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Validated
public interface PostService {

    Set<PostResponse> getAll();
}
