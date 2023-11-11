package com.demo.blog.blogpostservice.post;

import com.demo.blog.blogpostservice.post.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public PostResponse getPostById(@PathVariable("id") Long id) {
        return postService.getById(id);
    }

    @GetMapping
    public List<PostResponse> getAllPosts() {
        return postService.getAllOrderedByPublishedDateDesc();
    }

//    @PostMapping
//    @JsonView(View.PostCategory.class)
//    public PostResponse addCategories(PostCategoryRequest request) {}
}
