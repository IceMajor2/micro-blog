package com.demo.blog.blogpostservice.category.assertion;

import com.demo.blog.blogpostservice.assertion.HttpResponseAssert;
import com.demo.blog.blogpostservice.category.dto.CategoryResponse;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.springframework.data.util.Streamable;
import org.springframework.http.ResponseEntity;

public class CategoryRestAssert extends AbstractAssert<CategoryRestAssert, CategoryResponse> {

    private ResponseEntity<CategoryResponse> responseEntity;

    public CategoryRestAssert(ResponseEntity<CategoryResponse> actual) {
        super(actual.getBody(), CategoryRestAssert.class);
        this.responseEntity = actual;
    }

    public static CategoryRestAssert assertThat(ResponseEntity<CategoryResponse> actual) {
        return new CategoryRestAssert(actual);
    }

    /**
     * This assertion includes:
     * <ul>
     *     <li>{@code actual} and {@code expected} objects' equality</li>
     *     <li>{@code 200 OK} HTTP status code</li>
     * </ul>
     */
    public CategoryRestAssert isValidGetResponse(CategoryResponse expected) {
        HttpResponseAssert.assertThat(responseEntity).statusCodeIsOK();
        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
        return this;
    }

    /**
     * This assertion includes:
     * <ul>
     *     <li>{@code actual} and {@code expected} objects' equality</li>
     *     <li>{@code 201 Created} HTTP status code</li>
     * </ul>
     */
    public CategoryRestAssert isValidPostResponse(CategoryResponse expected) {
        HttpResponseAssert.assertThat(responseEntity).statusCodeIsCreated();
        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
        return this;
    }

    /**
     * This assertion includes:
     * <ul>
     *     <li>{@code actual} and {@code expected} objects' equality</li>
     *     <li>{@code 200 OK} HTTP status code</li>
     * </ul>
     */
    public CategoryRestAssert isValidPutResponse(CategoryResponse expected) {
        isValidGetResponse(expected);
//        HttpResponseAssert.assertThat(responseEntity).statusCodeIsOK();
//        Assertions.assertThat(actual).isEqualTo(expected);
        return this;
    }

    public CategoryRestAssert isValidDeleteResponse() {
        HttpResponseAssert.assertThat(responseEntity).statusCodeIsNoContent();
        return this;
    }


    public static class CategoryIterableRestAssert extends AbstractAssert<CategoryIterableRestAssert, Iterable<CategoryResponse>> {

        private ResponseEntity<Iterable<CategoryResponse>> responseEntity;

        CategoryIterableRestAssert(ResponseEntity<Iterable<CategoryResponse>> actual) {
            super(actual.getBody(), CategoryIterableRestAssert.class);
            this.responseEntity = actual;
        }

        public static CategoryIterableRestAssert assertThat(ResponseEntity<Iterable<CategoryResponse>> actual) {
            return new CategoryIterableRestAssert(actual);
        }

        public CategoryIterableRestAssert containsExactlyElementsOf(Iterable<CategoryResponse> expected) {
            Assertions.assertThat(actual)
                    .usingElementComparator(CategoryAssert.CATEGORY_RESPONSE_COMPARATOR)
                    .containsExactlyElementsOf(Streamable.of(expected));
            return this;
        }

        public CategoryIterableRestAssert areValidGetAllResponse(Iterable<CategoryResponse> expected) {
            HttpResponseAssert.assertThat(responseEntity).statusCodeIsOK();
            containsExactlyElementsOf(expected);
            return this;
        }
    }
}
