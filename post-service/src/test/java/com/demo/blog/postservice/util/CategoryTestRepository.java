package com.demo.blog.postservice.util;

import com.demo.blog.postservice.category.Category;
import com.demo.blog.postservice.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryTestRepository {

    private static CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        CategoryTestRepository.categoryRepository = categoryRepository;
    }

    public static Category getCategory(long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public static Iterable<Category> getAllCategoriesSorted() {
        return categoryRepository.findByOrderByNameAsc();
    }
}
