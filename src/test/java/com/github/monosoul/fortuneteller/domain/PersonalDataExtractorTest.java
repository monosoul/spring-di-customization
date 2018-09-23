package com.github.monosoul.fortuneteller.domain;

import com.github.monosoul.fortuneteller.model.FortuneRequest;
import lombok.val;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.assertj.core.api.Assertions.assertThat;

class PersonalDataExtractorTest {

    private static final int LIMIT = 10;

    @ParameterizedTest
    @MethodSource("requestStream")
    void apply(final FortuneRequest request) {
        val actual = new PersonalDataExtractor().apply(request);

        assertThat(actual).isNotNull()
                .isEqualToIgnoringGivenFields(request, "zodiacSign");
    }

    private static Stream<FortuneRequest> requestStream() {
        return Stream.generate(() ->
                FortuneRequest.builder()
                        .name(randomAlphabetic(LIMIT))
                        .zodiacSign(randomAlphabetic(LIMIT))
                        .age(nextInt())
                        .email(randomAlphabetic(LIMIT))
                        .build()
        ).limit(LIMIT);
    }
}