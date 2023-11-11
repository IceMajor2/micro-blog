package com.demo.blog.blogpostservice.exception;

import com.demo.blog.blogpostservice.exception.dto.ApiExceptionDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        String path = getPath(request);
        String message = getMessage(e.getFieldErrors());
        ApiException exception = new ApiException(HttpStatus.BAD_REQUEST, message, path);
        return ResponseEntity.badRequest().body(new ApiExceptionDTO(exception));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e,
                                                                        WebRequest request) {
        String path = getPath(request);
        String message = getMessage(e.getConstraintViolations());
        ApiException exception = new ApiException(HttpStatus.BAD_REQUEST, message, path);
        return ResponseEntity.badRequest().body(new ApiExceptionDTO(exception));
    }

    private String getPath(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }

    private String getMessage(Set<ConstraintViolation<?>> violations) {
        List<String> messages = new ArrayList<>();
        var iterator = violations.stream().iterator();
        while (iterator.hasNext()) {
            var violation = iterator.next();
            messages.add(violation.getMessage());
        }

        Collections.sort(messages);
        return String.join("; ", messages);
    }

    private String getMessage(List<FieldError> violations) {
        List<String> messages = new ArrayList<>();
        var iterator = violations.stream().iterator();
        while (iterator.hasNext()) {
            var violation = iterator.next();
            messages.add(violation.getDefaultMessage());
        }

        Collections.sort(messages);
        return String.join("; ", messages);
    }
}
