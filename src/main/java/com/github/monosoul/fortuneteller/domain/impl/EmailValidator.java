package com.github.monosoul.fortuneteller.domain.impl;

import java.util.function.Predicate;
import org.springframework.stereotype.Component;

@Component
public final class EmailValidator implements Predicate<String> {

    static final int MAX_LENGTH = 100;

    @Override
    public boolean test(final String input) {
        return input.length() <= MAX_LENGTH;
    }
}
