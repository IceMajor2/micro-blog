package com.demo.blog.blogpostservice;

import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.category.CategoryBuilder;
import com.demo.blog.blogpostservice.category.CategoryRepository;
import com.demo.blog.blogpostservice.post.Post;
import com.demo.blog.blogpostservice.post.PostRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "runner", name = "enable", havingValue = "true", matchIfMissing = false)
public class TestRunner {

    @Bean
    ApplicationRunner runner(CategoryRepository categoryRepository, PostRepository postRepository) {
        return args -> {
            categoryRepository.deleteAll();
            postRepository.deleteAll();

            Category testing = new CategoryBuilder()
                    .withName("Testing")
                    .build();
            categoryRepository.save(testing);

            Post mocking = Post.PostBuilder.post()
                    .withTitle("Introduction to mocking")
                    .withBody("If you want to extend your testing skills...")
                    .writtenBy(1)
                    .published().now()
                    .categories().addCategories(testing)
                    .build();
            postRepository.save(mocking);

            System.out.println(mocking);
            System.out.println(testing);

            System.out.println(categoryRepository.findByPostId(mocking.getId()));
        };
    }
}
