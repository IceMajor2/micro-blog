package com.demo.blog.blogpostservice.assertion;

import jakarta.validation.ConstraintViolation;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import java.util.Set;
import java.util.stream.Collectors;

public class JakartaValidationAssert extends AbstractAssert<JakartaValidationAssert, Set<ConstraintViolation<Object>>> {

    private Set<String> errorMessages;

    public JakartaValidationAssert(Set<ConstraintViolation<Object>> actual) {
        super(actual, JakartaValidationAssert.class);
        errorMessages = getErrorMessages();
    }

    public static JakartaValidationAssert assertThat(Set<ConstraintViolation<Object>> actual) {
        return new JakartaValidationAssert(actual);
    }

    public JakartaValidationAssert containsExceptionMessages(String... errorMessages) {
        isNotNull();
        Assertions.assertThat(this.errorMessages).contains(errorMessages);
        return this;
    }

    public JakartaValidationAssert containsOnlyExceptionMessages(String... errorMessages) {
        isNotNull();
        Assertions.assertThat(this.errorMessages).containsOnly(errorMessages);
        return this;
    }

    public JakartaValidationAssert isValid() {
        isNotNull();
        Assertions.assertThat(this.errorMessages)
                .isEmpty();
        return this;
    }

    private Set<String> getErrorMessages() {
        return actual.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());
    }
}
