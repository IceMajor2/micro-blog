package com.demo.blog.blogpostservice.category;

import com.demo.blog.blogpostservice.category.command.CategoryCommandCode;
import com.demo.blog.blogpostservice.category.dto.CategoryRequest;
import com.demo.blog.blogpostservice.category.dto.CategoryResponse;
import com.demo.blog.blogpostservice.command.CommandFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
class CategoryServiceImpl implements CategoryService {

    private final CommandFactory commandFactory;

    @Override
    public CategoryResponse getByName(String categoryName) {
        Category category = (Category) commandFactory
                .create(CategoryCommandCode.GET_CATEGORY_BY_NAME, categoryName)
                .execute();
        return new CategoryResponse(category);
    }

    @Override
    public CategoryResponse getById(Long categoryId) {
        Category category = (Category) commandFactory
                .create(CategoryCommandCode.GET_CATEGORY_BY_ID, categoryId)
                .execute();
        return new CategoryResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllOrderedByName() {
        Iterable<Category> categories = (Iterable<Category>) commandFactory
                .create(CategoryCommandCode.GET_CATEGORIES_SORTED_BY_NAME)
                .execute();
        return Streamable.of(categories)
                .stream()
                .map(CategoryResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = false)
    public CategoryResponse add(CategoryRequest request) {
        Category persisted = (Category) commandFactory
                .create(CategoryCommandCode.ADD_CATEGORY, request)
                .execute();
        log.info(STR. "Added category: '\{ persisted }'" );
        return new CategoryResponse(persisted);
    }

    @Override
    @Transactional(readOnly = false)
    public CategoryResponse replace(Long id, CategoryRequest request) {
        Category oldCategory = (Category) commandFactory
                .create(CategoryCommandCode.GET_CATEGORY_BY_ID, id)
                .execute();
        Category replaced = (Category) commandFactory
                .create(CategoryCommandCode.REPLACE_CATEGORY, oldCategory, request)
                .execute();
        log.info(STR. "Replaced category: '\{ oldCategory }' with: '\{ replaced }'" );
        return new CategoryResponse(replaced);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        Category deleted = (Category) commandFactory
                .create(CategoryCommandCode.DELETE_CATEGORY, id)
                .execute();
        log.info(STR. "Deleted category: '\{ deleted }'" );
    }
}
