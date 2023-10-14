package com.demo.blog.postservice.category;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public Category getCategory(@RequestParam("name") String categoryName) {
        return categoryService.get(categoryName);
    }

    @GetMapping
    public List<Category> getCategories() {
        return categoryService.getAll();
    }
}
