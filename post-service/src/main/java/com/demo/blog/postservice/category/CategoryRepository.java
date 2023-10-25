package com.demo.blog.postservice.category;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByName(String name);

    Iterable<Category> findByOrderByNameAsc();

    boolean existsByName(String name);
}
