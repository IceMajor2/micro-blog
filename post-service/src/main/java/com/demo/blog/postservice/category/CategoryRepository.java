package com.demo.blog.postservice.category;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, Long> {

    Optional<Category> findByName(String name);

    boolean existsByName(String name);
}
