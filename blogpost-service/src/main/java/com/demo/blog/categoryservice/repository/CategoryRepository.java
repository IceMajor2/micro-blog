package com.demo.blog.categoryservice.repository;

import com.demo.blog.categoryservice.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByName(String name);

    Iterable<Category> findByOrderByNameAsc();

    boolean existsByName(String name);

    default boolean exists(Category category) {
        return existsById(category.getId()) && existsByName(category.getName());
    }
}
