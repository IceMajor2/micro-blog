package com.demo.blog.categoryservice.environment.assertion;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public class HttpResponseAssert extends AbstractAssert<HttpResponseAssert, ResponseEntity<?>> {

    public HttpResponseAssert(ResponseEntity<?> actual) {
        super(actual, HttpResponseAssert.class);
    }

    public static HttpResponseAssert assertThat(ResponseEntity<?> actual) {
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
}
