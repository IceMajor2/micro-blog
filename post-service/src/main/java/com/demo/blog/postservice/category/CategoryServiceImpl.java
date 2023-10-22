package com.demo.blog.postservice.category;

import com.demo.blog.postservice.category.dto.CategoryRequest;
import com.demo.blog.postservice.category.dto.CategoryResponse;
import com.demo.blog.postservice.category.exception.CategoryAlreadyExistsException;
import com.demo.blog.postservice.category.exception.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse getByName(String categoryName) {
        if (categoryName == null)
            throw new NullPointerException("Category name was null");
        return new CategoryResponse(categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new CategoryNotFoundException(categoryName)));
    }

    @Override
    public CategoryResponse getById(Long id) {
        if (id == null)
            throw new NullPointerException("Category ID was null");
        return new CategoryResponse(categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id)));
    }

    @Override
    public Set<CategoryResponse> getAll() {
        return categoryRepository.findAllOrderByName().stream()
                .map(CategoryResponse::new)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    @Transactional(readOnly = false)
    public CategoryResponse add(CategoryRequest request) {
        Category category = new CategoryBuilder()
                .fromRequest(request)
                .build();
        if (categoryRepository.existsByName(category.getName()))
            throw new CategoryAlreadyExistsException(category.getName());
        return new CategoryResponse(categoryRepository.save(category));
    }

    @Override
    @Transactional(readOnly = false)
    public CategoryResponse replace(CategoryRequest request) {
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public CategoryResponse delete(Long id) {
        return null;
    }
}
