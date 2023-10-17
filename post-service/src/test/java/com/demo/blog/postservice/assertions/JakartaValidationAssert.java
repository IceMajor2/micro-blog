package com.demo.blog.postservice.assertions;

import jakarta.validation.ConstraintViolation;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import java.util.Set;
import java.util.stream.Collectors;

public class JakartaValidationAssert extends AbstractAssert<JakartaValidationAssert, Set<ConstraintViolation<Object>>> {

    public JakartaValidationAssert(Set<ConstraintViolation<Object>> actual) {
        super(actual, JakartaValidationAssert.class);
    }

    public static JakartaValidationAssert assertThat(Set<ConstraintViolation<Object>> actual) {
        return new JakartaValidationAssert(actual);
    }

    public JakartaValidationAssert containsOnlyExceptionMessages(String... errorMessages) {
        isNotNull();
        Assertions.assertThat(errorMessages())
                .containsOnly(errorMessages);
        return this;
    }

    private Set<String> errorMessages() {
        return actual.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());
    }
}
