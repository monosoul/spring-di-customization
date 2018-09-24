package com.github.monosoul.fortuneteller.web.converter;

import com.github.monosoul.fortuneteller.common.ZodiacSign;
import lombok.val;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static java.util.stream.Stream.generate;
import static org.apache.commons.lang3.RandomUtils.nextBoolean;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.assertj.core.api.Assertions.assertThat;

class ZodiacSignConverterTest {

    private static final int LIMIT = 10;

    @ParameterizedTest
    @MethodSource("zodiacSignStream")
    void apply(final ZodiacSign zodiacSign) {
        val input = randomizeCase(zodiacSign.toString());

        val actual = new ZodiacSignConverter().apply(input);

        assertThat(actual).isEqualByComparingTo(zodiacSign);
    }

    private String randomizeCase(final String input) {
        if (nextBoolean()) {
            return input.toLowerCase();
        }
        return input.toUpperCase();
    }

    private static Stream<ZodiacSign> zodiacSignStream() {
        return generate(() -> ZodiacSign.values()[nextInt(0, ZodiacSign.values().length)]).limit(LIMIT);
    }
}