package com.github.monosoul.fortuneteller.da;

import lombok.val;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.generate;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;

class RandomFortuneResponseRepositoryTest {

    private static final int LIMIT = 10;

    @ParameterizedTest
    @MethodSource("stringListStream")
    void get(final List<String> fortunes) {
        val actual = new RandomFortuneResponseRepository(fortunes).get();

        assertThat(actual).isNotNull();
        assertThat(actual.getMessage()).isIn(fortunes);
    }

    private static Stream<List<String>> stringListStream() {
        return generate(() -> stringStream().collect(toList())).limit(LIMIT);
    }

    private static Stream<String> stringStream() {
        return generate(() -> randomAlphabetic(LIMIT)).limit(LIMIT);
    }
}