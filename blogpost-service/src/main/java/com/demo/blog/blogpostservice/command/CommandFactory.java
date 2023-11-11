package com.demo.blog.blogpostservice.command;

import com.demo.blog.blogpostservice.author.Author;
import com.demo.blog.blogpostservice.author.AuthorRepository;
import com.demo.blog.blogpostservice.author.command.AuthorCommandCode;
import com.demo.blog.blogpostservice.author.command.GetAuthorByIdCommand;
import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.category.CategoryRepository;
import com.demo.blog.blogpostservice.category.command.*;
import com.demo.blog.blogpostservice.category.dto.CategoryRequest;
import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostRepository;
import com.demo.blog.blogpostservice.post.command.*;
import com.demo.blog.blogpostservice.post.dto.PostRequest;
import com.demo.blog.blogpostservice.postcategory.command.AddCategoriesToPostCommand;
import com.demo.blog.blogpostservice.postcategory.command.PostCategoryCommandCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommandFactory {

    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;

    public Command create(CommandCode commandCode, Object... params) {
        switch (commandCode) {
            case CategoryCommandCode.GET_CATEGORY_BY_ID:
                return new GetCategoryByIdCommand(categoryRepository, (Long) params[0]);
            case CategoryCommandCode.GET_CATEGORY_BY_NAME:
                return new GetCategoryByNameCommand(categoryRepository, (String) params[0]);
            case CategoryCommandCode.GET_CATEGORIES_SORTED_BY_NAME:
                return new GetCategoriesSortedByNameCommand(categoryRepository);
            case CategoryCommandCode.ADD_CATEGORY:
                return new AddCategoryCommand(categoryRepository, (CategoryRequest) params[0]);
            case CategoryCommandCode.REPLACE_CATEGORY:
                return new ReplaceCategoryCommand(categoryRepository, (Category) params[0], (CategoryRequest) params[1]);
            case CategoryCommandCode.DELETE_CATEGORY:
                return new DeleteCategoryCommand(categoryRepository, (Long) params[0]);
            case PostCommandCode.GET_ALL_POSTS:
                return new GetAllPostsCommand(postRepository);
            case PostCommandCode.GET_POST_BY_ID:
                return new GetPostByIdCommand(postRepository, (Long) params[0]);
            case PostCommandCode.GET_POST_CATEGORIES_SORTED_BY_NAME:
                return new GetPostCategoriesCommand(categoryRepository, postRepository, (Long) params[0]);
            case PostCommandCode.ADD_POST:
                return new AddPostCommand(postRepository, (PostRequest) params[0], (Author) params[1]);
            case PostCategoryCommandCode.ADD_CATEGORIES_TO_POST:
                return new AddCategoriesToPostCommand(postRepository, (Post) params[0], (List<Category>) params[1]);
            case AuthorCommandCode.GET_AUTHOR:
                return new GetAuthorByIdCommand(authorRepository, (Long) params[0]);
            default:
                return null;
        }
    }
}
