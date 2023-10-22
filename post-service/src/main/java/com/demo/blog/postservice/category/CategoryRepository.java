package com.demo.blog.postservice.category;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Set<Category> findByOrderByNameAsc();

    Optional<Category> findByName(String name);

    boolean existsByName(String name);
}
