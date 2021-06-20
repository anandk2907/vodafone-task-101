package com.example.vodafonetask101.exception;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -3916525550413865316L;

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
