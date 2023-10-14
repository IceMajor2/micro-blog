package com.demo.blog.postservice.category;

import com.demo.blog.postservice.category.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, Long> {

    Optional<Category> findByName(String name);
}
