package com.example.demo.exception;

import java.util.Map;

public class BadRequestException extends RuntimeException {
    private final Map<String, String> errors;

    public BadRequestException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

    public BadRequestException(String message) {
        super(message);
        this.errors = null;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
