package com.demo.blog.blogpostservice.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ApiException extends RuntimeException {

    private String timestamp;
    private HttpStatus status;
    private String error;
    private String message;
    private String path;

    public ApiException(HttpStatus status, String message, String path) {
        this.timestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now(Clock.systemUTC()));
        this.status = status;
        this.error = status.getReasonPhrase();
        this.message = message;
        this.path = path;
    }

    public ApiException(ApiExceptionDTO exceptionDTO) {
        this.timestamp = exceptionDTO.getTimestamp();
        this.status = HttpStatus.valueOf(exceptionDTO.getStatus());
        this.error = exceptionDTO.getError();
        this.message = exceptionDTO.getMessage();
        this.path = exceptionDTO.getPath();
    }
}
