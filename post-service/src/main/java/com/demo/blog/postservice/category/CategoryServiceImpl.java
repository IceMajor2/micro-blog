package com.demo.blog.postservice.category;

import com.demo.blog.postservice.category.dto.CategoryRequest;
import com.demo.blog.postservice.category.dto.CategoryResponse;
import com.demo.blog.postservice.category.exception.CategoryAlreadyExistsException;
import com.demo.blog.postservice.category.exception.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private static final String NULL_NAME_MSG = "Category name was null";
    private static final String NULL_ID_MSG = "Category ID was null";
    private static final String NULL_REQUEST_MSG = "Request was null";

    @Override
    public CategoryResponse getByName(String categoryName) {
        Objects.requireNonNull(categoryName, NULL_NAME_MSG);
        return new CategoryResponse(categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new CategoryNotFoundException(categoryName)));
    }

    @Override
    public CategoryResponse getById(Long id) {
        Objects.requireNonNull(id, NULL_ID_MSG);
        return new CategoryResponse(categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id)));
    }

    @Override
    public Set<CategoryResponse> getAll() {
        return Streamable.of(categoryRepository.findByOrderByNameAsc())
                .map(CategoryResponse::new)
                .stream()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    @Transactional(readOnly = false)
    public CategoryResponse add(CategoryRequest request) {
        Objects.requireNonNull(request, NULL_REQUEST_MSG);
        Category category = new CategoryBuilder()
                .fromRequest(request)
                .build();
        if (categoryRepository.existsByName(category.getName()))
            throw new CategoryAlreadyExistsException(category.getName());
        Category persisted = categoryRepository.save(category);
        log.info(STR. "Added category: '\{ persisted }'" );
        return new CategoryResponse(persisted);
    }

    @Override
    @Transactional(readOnly = false)
    public CategoryResponse replace(Long id, CategoryRequest request) {
        // null check
        Objects.requireNonNull(id, NULL_ID_MSG);
        Objects.requireNonNull(request, NULL_REQUEST_MSG);

        Category toReplace = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        Category replacement = new CategoryBuilder()
                .copy(toReplace)
                .withName(request.name())
                .build();
        if(categoryRepository.exists(replacement))
            throw new CategoryAlreadyExistsException(replacement.getName());
        Category replacementPersisted = categoryRepository.save(replacement);
        log.info(STR. "Replaced category: '\{ toReplace }' with: '\{ replacementPersisted }'" );
        return new CategoryResponse(replacementPersisted);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        if(!categoryRepository.existsById(id))
            throw new CategoryNotFoundException(id);
        categoryRepository.deleteById(id);
    }
}
