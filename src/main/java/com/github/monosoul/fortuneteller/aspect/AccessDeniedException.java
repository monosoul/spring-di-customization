package com.github.monosoul.fortuneteller.aspect;

public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException(final String message) {
        super(message);
    }
}
