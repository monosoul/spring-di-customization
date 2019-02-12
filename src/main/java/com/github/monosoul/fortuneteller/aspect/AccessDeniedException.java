package com.github.monosoul.fortuneteller.aspect;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = FORBIDDEN)
public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException(final String message) {
        super(message);
    }
}
