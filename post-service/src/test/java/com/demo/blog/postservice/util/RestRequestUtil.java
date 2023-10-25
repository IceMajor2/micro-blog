package com.demo.blog.postservice.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class RestRequestUtil {

    private static TestRestTemplate testRestTemplate;

    @Autowired
    public void setTestRestTemplate(TestRestTemplate testRestTemplate) {
        RestRequestUtil.testRestTemplate = testRestTemplate;
    }

    public static <T> ResponseEntity<T> get(String url, HttpMethod httpMethod, Class<T> responseType) {
        return get(url, httpMethod, null, responseType, new Object[0]);
    }

    public static <T> ResponseEntity<T> get(String url, HttpMethod httpMethod, ParameterizedTypeReference<T> responseType) {
        return get(url, httpMethod, null, responseType, new Object[0]);
    }

    public static <T> ResponseEntity<T> get(String url, HttpMethod httpMethod, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType, Object... params) {
        String built = buildURL(url, params);
        return testRestTemplate.exchange(built, httpMethod, requestEntity, responseType, params);
    }

    public static <T> ResponseEntity<T> get(String url, HttpMethod httpMethod, Class<T> responseType, Object... params) {
        return get(url, httpMethod, null, responseType, params);
    }

    public static <T> ResponseEntity<T> get(String url, HttpMethod httpMethod, HttpEntity<?> requestEntity, Class<T> responseType, Object... params) {
        String built = buildURL(url, params);
        return testRestTemplate.exchange(built, httpMethod, requestEntity, responseType, params);
    }

    private static String buildURL(String url, Object[] params) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        return builder.build(params).toString();
    }
}
