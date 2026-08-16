package com.fitzy.common.exception;

// TODO: extends RuntimeException — thrown when a request violates a domain rule. Maps to 409.
public class BusinessRuleViolationException extends RuntimeException {

    public BusinessRuleViolationException(String message) {
        super(message);
    }
}
