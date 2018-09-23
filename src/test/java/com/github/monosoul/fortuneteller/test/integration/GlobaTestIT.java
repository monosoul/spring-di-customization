package com.github.monosoul.fortuneteller.test.integration;

import com.github.monosoul.fortuneteller.domain.Globa;
import com.github.monosoul.fortuneteller.model.FortuneRequest;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.stream.Stream;

import static java.util.stream.Stream.generate;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

@SpringJUnitConfig
@ContextConfiguration(classes = TestITConfiguration.class)
public class GlobaTestIT {

    private static final int LIMIT = 10;

    private static final String[] EXPECTED_RESPONSES = new String[]{
            "You'll get married!",
            "Wow! You'll be very rich!",
            "You'll have 10 children!",
            "You'll meet your love soon!",
            "You'll get a promotion at your work in a year!",
            "You'll become very popular soon!"
    };

    @Autowired
    private Globa globa;

    @ParameterizedTest
    @MethodSource("validRequestStream")
    void getValidResponse(final FortuneRequest request) {
        val actual = globa.tell(request);

        assertThat(actual).isNotNull();
        assertThat(actual.getMessage()).isNotBlank()
                .isIn((Object[]) EXPECTED_RESPONSES);
    }

    @ParameterizedTest
    @MethodSource("invalidRequestStream")
    void exceptionWhenRequestIsInvalid(final FortuneRequest request) {
        assertThatThrownBy(() -> globa.tell(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getNpeWhenEmailIsNull() {
        assertThatThrownBy(() -> globa.tell(mock(FortuneRequest.class)))
                .isInstanceOf(NullPointerException.class);
    }

    private static Stream<FortuneRequest> validRequestStream() {
        return generate(() ->
                FortuneRequest.builder()
                        .name(randomAlphabetic(LIMIT))
                        .zodiacSign(randomAlphabetic(LIMIT))
                        .age(nextInt())
                        .email(randomAlphabetic(LIMIT))
                        .build()
        ).limit(LIMIT);
    }

    private static Stream<FortuneRequest> invalidRequestStream() {
        return generate(() ->
                FortuneRequest.builder()
                        .name(randomAlphabetic(LIMIT))
                        .zodiacSign(randomAlphabetic(LIMIT))
                        .age(nextInt())
                        .email(randomAlphabetic(101, 200))
                        .build()
        ).limit(LIMIT);
    }
}
