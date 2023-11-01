package com.demo.blog.postservice.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class RestRequestUtils {

    private static TestRestTemplate testRestTemplate;

    @Autowired
    public void setTestRestTemplate(TestRestTemplate testRestTemplate) {
        RestRequestUtils.testRestTemplate = testRestTemplate;
    }

    public static <T> ResponseEntity<T> get(String url, Class<T> responseType) {
        return exchange(url, HttpMethod.GET, null, responseType);
    }

    public static <T> ResponseEntity<T> get(String url, ParameterizedTypeReference<T> responseType) {
        return exchange(url, HttpMethod.GET, null, responseType);
    }

    public static <T> ResponseEntity<T> get(String url, Class<T> responseType, Object... params) {
        return exchange(url, HttpMethod.GET, null, responseType, params);
    }

    public static <T> ResponseEntity<T> get(String url, HttpEntity<?> requestEntity, Class<T> responseType, Object... params) {
        return exchange(url, HttpMethod.GET, requestEntity, responseType, params);
    }

    public static <T> ResponseEntity<T> post(String url, Object request, Class<T> responseType) {
        return exchange(url, HttpMethod.POST, new HttpEntity<>(request), responseType);
    }

    public static <T> ResponseEntity<T> put(String url, Object request, Class<T> responseType, Object... params) {
        return exchange(url, HttpMethod.PUT, new HttpEntity<>(request), responseType, params);
    }

    public static <T> ResponseEntity<T> delete(String url, Class<T> responseType, Object... params) {
        return exchange(url, HttpMethod.DELETE, null, responseType, params);
    }

    private static <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity,
                                                  ParameterizedTypeReference<T> responseType, Object... params) {
        return testRestTemplate.exchange(url, method, requestEntity, responseType, params);
    }

    private static <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity,
                                                  Class<T> responseType, Object... params) {
        return testRestTemplate.exchange(url, method, requestEntity, responseType, params);
    }
}
