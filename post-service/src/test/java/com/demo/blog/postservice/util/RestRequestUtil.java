package com.demo.blog.postservice.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class RestRequestUtil {

    private static TestRestTemplate testRestTemplate;

    @Autowired
    public void setTestRestTemplate(TestRestTemplate testRestTemplate) {
        RestRequestUtil.testRestTemplate = testRestTemplate;
    }

    public static <T> ResponseEntity<T> get(String url, Class<T> responseType) {
        return get(url, null, responseType, new Object[0]);
    }

    public static <T> ResponseEntity<T> get(String url, ParameterizedTypeReference<T> responseType) {
        return get(url, null, responseType, new Object[0]);
    }

    public static <T> ResponseEntity<T> get(String url, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType, Object... params) {
        return testRestTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType, params);
    }

    public static <T> ResponseEntity<T> get(String url, Class<T> responseType, Object... params) {
        return get(url, null, responseType, params);
    }

    public static <T> ResponseEntity<T> get(String url, HttpEntity<?> requestEntity, Class<T> responseType, Object... params) {
        return testRestTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType, params);
    }

    public static <T> ResponseEntity<T> post(String url, Object request, Class<T> responseType) {
        return testRestTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(request), responseType);
    }
}
