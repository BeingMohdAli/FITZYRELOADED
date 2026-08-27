package com.fitzy.common.exception;

import org.springframework.web.client.RestClientException;

// TODO: extends RuntimeException — thrown when a downstream call (Gemini, Kafka, etc.) fails. Maps to 503.
public class ExternalServiceException extends RuntimeException{
    public ExternalServiceException(String message, RestClientException lastError) {
        super(message,lastError);
    }
}
