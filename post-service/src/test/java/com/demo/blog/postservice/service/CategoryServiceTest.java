package com.demo.blog.postservice.service;

import com.demo.blog.postservice.exception.CategoryNotFoundException;
import com.demo.blog.postservice.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;

@TestClassOrder(ClassOrderer.Random.class)
public class CategoryServiceTest {

    private CategoryService SUT;
    private CategoryRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(CategoryRepository.class);
        SUT = new CategoryService(repository);
    }

    @Test
    void shouldThrowExceptionOnCategoryNotAdded() {
        assertThatExceptionOfType(CategoryNotFoundException.class)
                .isThrownBy(() -> SUT.get("Java"));
    }
}
