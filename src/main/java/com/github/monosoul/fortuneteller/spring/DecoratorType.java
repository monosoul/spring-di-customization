package com.github.monosoul.fortuneteller.spring;

import lombok.NonNull;

public enum DecoratorType {
    LOGGING("Logging"),
    CACHING("Caching"),
    NOT_DECORATOR("");

    private final String prefix;

    DecoratorType(@NonNull final String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
