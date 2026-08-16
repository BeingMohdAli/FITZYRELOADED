package com.fitzy.common.exception;

// TODO: extends RuntimeException — thrown when a downstream call (Gemini, Kafka, etc.) fails. Maps to 503.
public class ExternalServiceException extends RuntimeException{
    public ExternalServiceException(String message) {
        super(message);
    }
}
