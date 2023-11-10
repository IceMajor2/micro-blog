package com.demo.blog.blogpostservice.assertion;

import com.demo.blog.blogpostservice.category.dto.CategoryResponse;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public class HttpResponseAssert<T> extends AbstractAssert<HttpResponseAssert<T>, ResponseEntity<T>> {

    public HttpResponseAssert(ResponseEntity<T> actual) {
        super(actual, HttpResponseAssert.class);
    }

    public static <T> HttpResponseAssert assertThat(ResponseEntity<?> actual) {
        return new HttpResponseAssert(actual);
    }

    public HttpResponseAssert statusCodeIs(HttpStatusCode expectedStatusCode) {
        isNotNull();
        Assertions.assertThat(actual.getStatusCode())
                .as("status code")
                .isEqualTo(expectedStatusCode);
        return this;
    }

    public HttpResponseAssert locationHeaderContains(String location) {
        isNotNull();
        URI actualLocation = actual.getHeaders().getLocation();
        Assertions.assertThat(actualLocation).hasPath(location);
        return this;
    }

    public HttpResponseAssert statusCodeIsOK() {
        statusCodeIs(HttpStatus.OK);
        return this;
    }

    public HttpResponseAssert statusCodeIsCreated() {
        statusCodeIs(HttpStatus.CREATED);
        return this;
    }

    public HttpResponseAssert statusCodeIsNoContent() {
        statusCodeIs(HttpStatus.NO_CONTENT);
        return this;
    }

    public HttpResponseAssert ignoringIdEqualTo(T expected) {
        isNotNull();
        Assertions.assertThat(actual.getBody())
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
        return this;
    }

    public HttpResponseAssert ignoringIdContainsExactlyElementsOf(Iterable<CategoryResponse> expected) {
        isNotNull();
        Iterable<CategoryResponse> actualBody = (Iterable<CategoryResponse>) actual.getBody();
        Assertions.assertThat(actualBody)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactlyElementsOf(expected);
        return this;
    }
}
