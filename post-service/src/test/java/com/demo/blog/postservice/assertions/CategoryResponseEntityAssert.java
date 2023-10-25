package com.demo.blog.postservice.assertions;

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
     * This assertion includes:
     * <ul>
     *     <li>{@code actual} and {@code expected} objects' equality</li>
     *     <li>{@code 200 OK} HTTP status code</li>
     * </ul>
     */
    public CategoryResponseEntityAssert isValidGetResponse(CategoryResponse expected) {
        HttpResponseAssert.assertThat(responseEntity).statusCodeIsOK();
        Assertions.assertThat(actual).isEqualTo(expected);
        return this;
    }

    /**
     * This assertion includes:
     * <ul>
     *     <li>{@code actual} and {@code expected} objects' equality</li>
     *     <li>{@code 201 Created} HTTP status code</li>
     * </ul>
     */
    public void isValidPostResponse(CategoryResponse expected) {
        HttpResponseAssert.assertThat(responseEntity).statusCodeIsCreated();
        Assertions.assertThat(actual).isEqualTo(expected);
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

        public CategoriesResponseEntityAssert containsExactlyElementsOf(Iterable<CategoryResponse> expected) {
            Assertions.assertThat(actual)
                    .usingElementComparator(CategoryAssert.CATEGORY_RESPONSE_COMPARATOR)
                    .containsExactlyElementsOf(Streamable.of(expected));
            return this;
        }

        public CategoriesResponseEntityAssert areValidGetAllResponse(Iterable<CategoryResponse> expected) {
            HttpResponseAssert.assertThat(responseEntity).statusCodeIsOK();
            containsExactlyElementsOf(expected);
            return this;
        }
    }
}
