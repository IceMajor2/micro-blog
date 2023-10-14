package com.demo.blog.postservice.repository;

import com.demo.blog.postservice.domain.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, Long> {
}
