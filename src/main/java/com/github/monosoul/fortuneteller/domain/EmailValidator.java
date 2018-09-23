package com.github.monosoul.fortuneteller.domain;

import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class EmailValidator implements Predicate<String> {

    static final int MAX_LENGTH = 100;

    @Override
    public boolean test(final String input) {
        return input.length() <= MAX_LENGTH;
    }
}
