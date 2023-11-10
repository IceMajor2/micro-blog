package com.demo.blog.blogpostservice.category.assertion;

import com.demo.blog.blogpostservice.category.Category;
import com.demo.blog.blogpostservice.category.CategoryRepository;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import java.util.Optional;

public class CategoryRepositoryAssert extends AbstractAssert<CategoryRepositoryAssert, CategoryRepository> {

    public CategoryRepositoryAssert(CategoryRepository actual) {
        super(actual, CategoryRepositoryAssert.class);
    }

    public static CategoryRepositoryAssert assertThat(CategoryRepository actual) {
        return new CategoryRepositoryAssert(actual);
    }

    public CategoryRepositoryAssert hasSize(long size) {
        isNotNull();
        Assertions.assertThat(actual.count()).isEqualTo(size);
        return this;
    }

    public CategoryRepositoryAssert persisted(Category expectedCategory) {
        isNotNull();
        boolean existsByName = actual.existsByName(expectedCategory.getName());
        Assertions.assertThat(existsByName)
                .withFailMessage(STR."Did not find category of \{ expectedCategory.getName() } name in the repository")
                .isTrue();
        Optional<Category> optionalActual = actual.findById(expectedCategory.getId());
        Assertions.assertThat(optionalActual)
                .withFailMessage(STR."Did not find category of '\{ expectedCategory.getId() }' id")
                .isPresent();
        Category actual = optionalActual.get();
        Assertions.assertThat(actual).isEqualTo(expectedCategory);
        return this;
    }
}
