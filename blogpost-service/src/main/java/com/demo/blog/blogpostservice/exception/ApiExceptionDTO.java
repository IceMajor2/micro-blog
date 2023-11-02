package com.demo.blog.blogpostservice.exception;

import lombok.Data;

@Data
public class ApiExceptionDTO {

    private final String timestamp;
    private final Integer status;
    private final String error;
    private final String message;
    private final String path;

    public ApiExceptionDTO(ApiException exception) {
        this.timestamp = exception.getTimestamp();
        this.status = exception.getStatus().value();
        this.error = exception.getError();
        this.message = exception.getMessage();
        this.path = exception.getPath();
    }

    public ApiExceptionDTO(String timestamp, Integer status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
