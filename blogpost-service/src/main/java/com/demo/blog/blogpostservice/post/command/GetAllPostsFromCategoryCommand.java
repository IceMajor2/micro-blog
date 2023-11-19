package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.command.Command;
import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Streamable;

import java.util.List;

@RequiredArgsConstructor
public class GetAllPostsFromCategoryCommand implements Command<List<Post>> {

    private final PostRepository postRepository;
    private final Category category;

    @Override
    public List<Post> execute() {
        return Streamable.of(postRepository.findByCategoryOrderByPublishedOnDesc(category.getId()))
                .toList();
    }
}
