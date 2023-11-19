package com.demo.blog.blogpostservice.category.datasupply;

import com.demo.blog.blogpostservice.category.dto.CategoryResponse;
import org.springframework.core.ParameterizedTypeReference;

import java.util.Comparator;
import java.util.List;

public class CategoryConstants {

    public static final String API_CATEGORY = "/api/category";
    public static final String API_CATEGORY_ID = "/api/category/{id}";
    public static final String API_CATEGORY_NAME = "/api/category?name={name}";
    public static final String API_CATEGORY_SLASH = API_CATEGORY + '/';

    public static final String EXISTS_MSG_T = "Category '%s' already exists";
    public static final String ID_NOT_FOUND_MSG_T = "Category of '%d' ID was not found";
    public static final String NAME_NOT_FOUND_MSG_T = "Category '%s' was not found";
    public static final String BLANK_MSG = "Category name must be specified";
    public static final String TOO_LONG_MSG = "Category name cannot exceed 32 characters";
    public static final String CATEGORIES_EMPTY_MSG = "List of categories was empty";

    public static final String NULL_NAME_MSG = "Category name was null";
    public static final String NULL_ID_MSG = "Category ID was null";
    public static final String NULL_REQUEST_MSG = "Request was null";
    public static final String NULL_OLD_CATEGORY_MSG = "Category specified to be replaced was null";
    public static final String NULL_CATEGORIES_MSG = "Categories were null";
    public static final String NULL_CATEGORY_MSG = "Category was null";

    public static final Comparator<CategoryResponse> CATEGORY_RESPONSE_COMPARATOR = Comparator.comparing(CategoryResponse::name);

    public static final ParameterizedTypeReference<List<CategoryResponse>> PARAMETERIZED_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {};
}
