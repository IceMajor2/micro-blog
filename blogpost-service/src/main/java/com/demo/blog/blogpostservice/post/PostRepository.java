package com.demo.blog.blogpostservice.post;

import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {

    Iterable<Post> findByOrderByPublishedOnDesc();

    Iterable<Post> findByAuthorOrderByPublishedOnDesc(Long authorId);

    boolean existsByTitle(String title);
}
