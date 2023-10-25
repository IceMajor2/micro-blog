package com.demo.blog.postservice.assertions;

import com.demo.blog.postservice.category.Category;
import com.demo.blog.postservice.category.dto.CategoryResponse;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.springframework.data.util.Streamable;
import org.springframework.http.ResponseEntity;

public class CategoryResponseEntityAssert extends AbstractAssert<CategoryResponseEntityAssert, CategoryResponse> {

    private ResponseEntity<CategoryResponse> responseEntity;

    public CategoryResponseEntityAssert(ResponseEntity<CategoryResponse> actual) {
        super(actual.getBody(), CategoryResponseEntityAssert.class);
        this.responseEntity = actual;
    }

    public static CategoryResponseEntityAssert assertThat(ResponseEntity<CategoryResponse> actual) {
        return new CategoryResponseEntityAssert(actual);
    }

    /**
     * Asserts field of domain model (i.e. {@code Category} object)
     * are equal to the ones of the response DTO
     * (i.e. {@code CategoryResponse} object).
     *
     * @param expected
     */
    public CategoryResponseEntityAssert matchesModel(Category expected) {
        isNotNull();
        Assertions.assertThat(actual)
                .usingComparator(CategoryAssert.CATEGORY_RESPONSE_COMPARATOR)
                .isEqualTo(new CategoryResponse(expected));
        return this;
    }

    /**
     * A custom assertion that checks for the average HTTP GET request's response.
     * Namely, it asserts the response's status code is {@code 200 OK} and that
     * the {@code expected} object matches actual (through {@link CategoryResponseEntityAssert#matchesModel}).
     *
     * @param expected expected {@code Category} object
     */
    public CategoryResponseEntityAssert isValidGetResponse(Category expected) {
        HttpResponseAssert.assertThat(responseEntity).statusCodeIsOK();
        matchesModel(expected);
        return this;
    }


    public static class CategoriesResponseEntityAssert extends AbstractAssert<CategoriesResponseEntityAssert, Iterable<CategoryResponse>> {

        private ResponseEntity<Iterable<CategoryResponse>> responseEntity;

        CategoriesResponseEntityAssert(ResponseEntity<Iterable<CategoryResponse>> actual) {
            super(actual.getBody(), CategoriesResponseEntityAssert.class);
            this.responseEntity = actual;
        }

        public static CategoriesResponseEntityAssert assertThat(ResponseEntity<Iterable<CategoryResponse>> actual) {
            return new CategoriesResponseEntityAssert(actual);
        }

        public CategoriesResponseEntityAssert containsExactlyElementsOf(Iterable<Category> expected) {
            Assertions.assertThat(actual)
                    .usingElementComparator(CategoryAssert.CATEGORY_RESPONSE_COMPARATOR)
                    .containsExactlyElementsOf(Streamable.of(expected).map(CategoryResponse::new));
            return this;
        }

        public CategoriesResponseEntityAssert areValidGetAllResponse(Iterable<Category> expected) {
            HttpResponseAssert.assertThat(responseEntity).statusCodeIsOK();
            containsExactlyElementsOf(expected);
            return this;
        }
    }
}
