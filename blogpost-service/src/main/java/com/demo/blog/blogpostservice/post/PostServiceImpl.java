package com.demo.blog.blogpostservice.post;

import com.demo.blog.blogpostservice.author.Author;
import com.demo.blog.blogpostservice.author.command.AuthorCommandCode;
import com.demo.blog.blogpostservice.command.CommandFactory;
import com.demo.blog.blogpostservice.post.command.PostCommandCode;
import com.demo.blog.blogpostservice.post.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        return new PostResponse(post, author, Collections.emptyList());
    }

    @Override
    public List<PostResponse> getAllOrderedByPublishedDateDesc() {
        List<Post> posts = (List<Post>) commandFactory
                .create(PostCommandCode.GET_ALL_POSTS)
                .execute();
        return posts.stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }
}
