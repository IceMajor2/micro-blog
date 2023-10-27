package com.demo.blog.postservice.category;

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

    public static long getCategoriesSize() {
        return categoryRepository.count();
    }
}
