package com.demo.blog.postservice.repository;

import com.demo.blog.postservice.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, Long> {
}
