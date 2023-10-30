package com.demo.blog.postservice.category;

import com.demo.blog.postservice.category.dto.CategoryResponse;
import org.springframework.core.ParameterizedTypeReference;

public class Constants {

    public static final String API_CATEGORY = "/api/category";
    public static final String API_CATEGORY_ID = "/api/category/{id}";
    public static final String API_CATEGORY_NAME = "/api/category?name={name}";
    public static final String API_CATEGORY_SLASH = API_CATEGORY + '/';

    public static final String CATEGORY_EXISTS_MSG_TEMPL = "Category '%s' already exists";
    public static final String ID_NOT_FOUND_MSG_TEMPL = "Category of '%d' ID was not found";
    public static final String NAME_NOT_FOUND_MSG_TEMPL = "Category '%s' was not found";
    public static final String NOT_BLANK_MSG = "Category name must be specified";
    public static final String NAME_TOO_LONG_MSG = "Category name cannot exceed 32 characters";
    public static final String NAME_NULL_MSG = "Category name was null";
    public static final String ID_NULL_MSG = "Category ID was null";

    public static final Long ANY_LONG = 1L;
    public static final Long NEGATIVE_LONG = -1L;
    public static final String ANY_STRING = "ANY_STRING";

    public static final ParameterizedTypeReference<Iterable<CategoryResponse>> PARAMETERIZED_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {};
}
