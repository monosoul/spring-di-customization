package com.github.monosoul.fortuneteller.domain;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.github.monosoul.fortuneteller.domain.EmailValidator.MAX_LENGTH;
import static java.util.stream.Stream.generate;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailValidatorTest {

    private static final int LIMIT = 10;

    @ParameterizedTest
    @MethodSource("validStringStream")
    void trueIfShorterThanMaxLength(final String input) {
        val actual = new EmailValidator().test(input);

        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @MethodSource("invalidStringStream")
    void falseIfLongerThanMaxLength(final String input) {
        val actual = new EmailValidator().test(input);

        assertThat(actual).isFalse();
    }

    @Test
    void npeIfEmailIsNull() {
        assertThatThrownBy(() -> new EmailValidator().test(null))
                .isInstanceOf(NullPointerException.class);
    }

    private static Stream<String> validStringStream() {
        return generate(() -> randomAlphabetic(MAX_LENGTH)).limit(LIMIT);
    }

    private static Stream<String> invalidStringStream() {
        return generate(() -> randomAlphabetic(MAX_LENGTH + 1, MAX_LENGTH * 2)).limit(LIMIT);
    }
}