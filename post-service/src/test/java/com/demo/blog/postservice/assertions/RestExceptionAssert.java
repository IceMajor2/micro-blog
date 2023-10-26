package com.demo.blog.postservice.assertions;

import com.demo.blog.postservice.exception.ApiException;
import com.demo.blog.postservice.exception.ApiExceptionDTO;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RestExceptionAssert extends AbstractAssert<RestExceptionAssert, ApiException> {

    private ResponseEntity<ApiExceptionDTO> responseEntity;

    public RestExceptionAssert(ResponseEntity<ApiExceptionDTO> actual) {
        super(new ApiException(actual.getBody()), RestExceptionAssert.class);
        this.responseEntity = actual;
    }

    public static RestExceptionAssert assertThatException(ResponseEntity<ApiExceptionDTO> actual) {
        return new RestExceptionAssert(actual);
    }

    public RestExceptionAssert isCoded(HttpStatus expectedStatusCode) {
        HttpResponseAssert.assertThat(responseEntity).statusCodeIs(expectedStatusCode);
        return this;
    }

    public RestExceptionAssert isBadRequest() {
        isCoded(HttpStatus.BAD_REQUEST);
        return this;
    }

    public RestExceptionAssert isNotFound() {
        isCoded(HttpStatus.NOT_FOUND);
        return this;
    }

    public RestExceptionAssert isConflict() {
        isCoded(HttpStatus.CONFLICT);
        return this;
    }

    public RestExceptionAssert withMessage(String expectedMessage) {
        isNotNull();
        Assertions.assertThat(actual.getMessage()).isEqualTo(expectedMessage);
        return this;
    }

    public RestExceptionAssert withPath(String expectedPath) {
        isNotNull();
        Assertions.assertThat(actual.getPath()).isEqualTo(expectedPath);
        return this;
    }
}
