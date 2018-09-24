package com.github.monosoul.fortuneteller.web;

import com.github.monosoul.fortuneteller.common.ZodiacSign;
import com.github.monosoul.fortuneteller.domain.HoroscopeTeller;
import com.github.monosoul.fortuneteller.model.Horoscope;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;

import java.util.function.Function;
import java.util.stream.Stream;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class HoroscopeTellControllerTest {

    private static final int LIMIT = 10;

    @Mock
    private HoroscopeTeller horoscopeTeller;
    @Mock
    private Horoscope horoscope;
    @Mock
    private Function<String, ZodiacSign> zodiacSignConverter;
    @Mock
    private ZodiacSign zodiacSign;

    @BeforeEach
    void setUp() {
        initMocks(this);

        when(zodiacSignConverter.apply(anyString())).thenReturn(zodiacSign);
        when(horoscopeTeller.tell(any(ZodiacSign.class))).thenReturn(horoscope);
    }

    @ParameterizedTest
    @MethodSource("stringStream")
    void tell(final String sign) {
        val actual = new HoroscopeTellController(horoscopeTeller, zodiacSignConverter).tell(sign);

        verify(zodiacSignConverter).apply(sign);
        verify(horoscopeTeller).tell(zodiacSign);
        verifyNoMoreInteractions(zodiacSignConverter, horoscopeTeller);

        assertThat(actual).isSameAs(horoscope);
    }

    private static Stream<String> stringStream() {
        return Stream.generate(() -> randomAlphabetic(LIMIT)).limit(LIMIT);
    }
}