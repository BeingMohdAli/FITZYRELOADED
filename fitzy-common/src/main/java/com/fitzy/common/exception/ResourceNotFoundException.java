package com.fitzy.common.exception;

// TODO: extends RuntimeException — thrown when a requested entity doesnt exist. Maps to 404.
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
