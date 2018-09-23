package com.github.monosoul.fortuneteller.domain;

import com.github.monosoul.fortuneteller.model.FortuneRequest;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;

import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Stream.generate;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class FortuneRequestValidatorTest {

    private static final int LIMIT = 10;

    @Mock
    private Predicate<String> emailValidator;
    @Mock
    private FortuneRequest request;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @ParameterizedTest
    @MethodSource("stringStream")
    void trueIfAllIsValid(final String email) {
        when(request.getEmail()).thenReturn(email);
        when(emailValidator.test(email)).thenReturn(true);

        val actual = new FortuneRequestValidator(emailValidator).test(request);

        verify(emailValidator).test(email);
        verifyNoMoreInteractions(emailValidator);

        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @MethodSource("stringStream")
    void falseIfAnythingIsInvalid(final String email) {
        when(request.getEmail()).thenReturn(email);
        when(emailValidator.test(anyString())).thenReturn(false);

        val actual = new FortuneRequestValidator(emailValidator).test(request);

        verify(emailValidator).test(email);
        verifyNoMoreInteractions(emailValidator);

        assertThat(actual).isFalse();
    }

    private static Stream<String> stringStream() {
        return generate(() -> randomAlphabetic(LIMIT)).limit(LIMIT);
    }
}