package com.demo.blog.blogpostservice.postcategory.datasupply;

import com.demo.blog.blogpostservice.postcategory.dto.PostCategoryRequest;

import java.util.List;

import static com.demo.blog.blogpostservice.category.datasupply.CategoryDataSupply.CONTAINERS_CATEGORY;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.DOCKER_POST;
import static com.demo.blog.blogpostservice.post.datasupply.PostDataSupply.SPRING_POST;

public class PostCategoryDataSupply {

    public static final PostCategoryRequest DOCKER_W_CONTAINERS_SPRING_REQ =
            new PostCategoryRequest(DOCKER_POST.getId(), List.of(CONTAINERS_CATEGORY.getId(), SPRING_POST.getId()));
}
