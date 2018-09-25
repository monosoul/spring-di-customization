package com.github.monosoul.fortuneteller.domain;

import static java.util.stream.Stream.generate;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.stream.Stream;
import lombok.val;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class NameNormalizerTest {

    private static final int LIMIT = 10;

    @ParameterizedTest
    @MethodSource("stringStream")
    void apply(final String name) {
        val actual = new NameNormalizer().apply(name);

        assertThat(actual.charAt(0)).isUpperCase();
        assertThat(actual.substring(1)).isEqualTo(name.substring(1).toLowerCase());
    }

    private static Stream<String> stringStream() {
        return generate(() -> randomAlphabetic(LIMIT)).limit(LIMIT);
    }
}