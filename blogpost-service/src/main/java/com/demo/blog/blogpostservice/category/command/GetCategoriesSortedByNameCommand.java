package com.demo.blog.blogpostservice.category.command;

import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.category.CategoryRepository;
import com.demo.blog.blogpostservice.command.Command;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetCategoriesSortedByNameCommand implements Command {

    private final CategoryRepository categoryRepository;

    @Override
    public Iterable<Category> execute() {
        return categoryRepository.findByOrderByNameAsc();
    }
}
