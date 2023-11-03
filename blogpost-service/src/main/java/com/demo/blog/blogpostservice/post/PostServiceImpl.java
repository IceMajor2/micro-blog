package com.demo.blog.blogpostservice.post;

import com.demo.blog.blogpostservice.command.CommandFactory;
import com.demo.blog.blogpostservice.post.command.PostCommandCode;
import com.demo.blog.blogpostservice.post.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final CommandFactory commandFactory;

    @Override
    public Set<PostResponse> getAll() {
        Set<Post> posts = (Set<Post>) commandFactory
                .create(PostCommandCode.GET_ALL_POSTS)
                .execute();
        return posts.stream()
                .map(post -> new PostResponse(post.getId(), post.getTitle(), post.getBody()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
