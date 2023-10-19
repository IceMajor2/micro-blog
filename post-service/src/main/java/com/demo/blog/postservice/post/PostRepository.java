package com.demo.blog.postservice.post;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, Long> {
    Optional<Post> findByTitle(String title);
}
