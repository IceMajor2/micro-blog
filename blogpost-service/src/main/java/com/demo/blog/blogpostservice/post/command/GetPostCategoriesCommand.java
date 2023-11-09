package com.demo.blog.blogpostservice.post.command;

import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.category.CategoryRepository;
import com.demo.blog.blogpostservice.command.Command;
import com.demo.blog.blogpostservice.post.PostRepository;
import com.demo.blog.blogpostservice.post.exception.PostExceptionMessage;
import com.demo.blog.blogpostservice.post.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Streamable;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class GetPostCategoriesCommand implements Command {

    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    private final Long postId;

    @Override
    public List<Category> execute() {
        Objects.requireNonNull(postId, PostExceptionMessage.NULL_ID_MSG.getMessage());
        if(!postRepository.existsById(postId))
            throw new PostNotFoundException(postId);
        return Streamable.of(categoryRepository.findByPostId(postId)).toList();
    }
}
