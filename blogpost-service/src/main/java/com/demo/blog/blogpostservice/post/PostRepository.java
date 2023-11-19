package com.demo.blog.blogpostservice.post;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends CrudRepository<Post, Long> {

    Iterable<Post> findByOrderByPublishedOnDesc();

    Iterable<Post> findByAuthorOrderByPublishedOnDesc(Long authorId);

    @Query("SELECT p.* FROM post p JOIN post_category pc ON p.id = pc.category_id WHERE pc.category_id = :id")
    Iterable<Post> findByCategoryOrderByPublishedOnDesc(@Param("id") Long categoryId);

    boolean existsByTitle(String title);
}
