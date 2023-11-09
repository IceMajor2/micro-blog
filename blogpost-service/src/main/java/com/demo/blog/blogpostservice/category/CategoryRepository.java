package com.demo.blog.blogpostservice.category;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByName(String name);

    Iterable<Category> findByOrderByNameAsc();

    @Query("SELECT c.* FROM category c JOIN post_category pc ON c.id = pc.post_id WHERE pc.post_id = :id")
    Iterable<Category> findByPostId(@Param("id") Long id);

    boolean existsByName(String name);

    default boolean exists(Category category) {
        return existsById(category.getId()) && existsByName(category.getName());
    }
}
