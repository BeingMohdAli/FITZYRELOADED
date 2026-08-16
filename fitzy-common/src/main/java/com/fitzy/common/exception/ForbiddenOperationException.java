package com.fitzy.common.exception;

// Thrown when an authenticated caller accesses a resource they don't own. Maps to 403.
public class ForbiddenOperationException extends RuntimeException {

    public ForbiddenOperationException(String message) {
        super(message);
    }
}