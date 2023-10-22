package com.demo.blog.postservice.category;

import com.demo.blog.postservice.category.dto.CategoryRequest;
import com.demo.blog.postservice.category.dto.CategoryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable("id") Long id) {
        return categoryService.getById(id);
    }

    @GetMapping(params = "name")
    public CategoryResponse getCategoryByName(@RequestParam("name") String categoryName) {
        return categoryService.getByName(categoryName);
    }

    @GetMapping
    public List<CategoryResponse> getCategories() {
        return categoryService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse addCategory(@RequestBody @Valid CategoryRequest request) {
        return categoryService.add(request);
    }
}
