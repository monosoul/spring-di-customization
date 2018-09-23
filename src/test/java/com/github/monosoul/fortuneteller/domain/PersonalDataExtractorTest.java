package com.github.monosoul.fortuneteller.domain;

import com.github.monosoul.fortuneteller.model.FortuneRequest;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;

import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Stream.generate;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class PersonalDataExtractorTest {

    private static final int LIMIT = 10;

    @Mock
    private Predicate<FortuneRequest> requestValidator;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @ParameterizedTest
    @MethodSource("requestStream")
    void apply(final FortuneRequest request) {
        when(requestValidator.test(any(FortuneRequest.class))).thenReturn(true);

        val actual = new PersonalDataExtractor(requestValidator).apply(request);

        assertThat(actual).isNotNull()
                .isEqualToIgnoringGivenFields(request, "zodiacSign");
    }

    @Test
    void exceptionIfRequestIsInvalid() {
        val request = mock(FortuneRequest.class);

        when(requestValidator.test(any(FortuneRequest.class))).thenReturn(false);

        assertThatThrownBy(() -> new PersonalDataExtractor(requestValidator).apply(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid request!");

    }

    private static Stream<FortuneRequest> requestStream() {
        return generate(() ->
                FortuneRequest.builder()
                        .name(randomAlphabetic(LIMIT))
                        .zodiacSign(randomAlphabetic(LIMIT))
                        .age(nextInt())
                        .email(randomAlphabetic(LIMIT))
                        .build()
        ).limit(LIMIT);
    }
}