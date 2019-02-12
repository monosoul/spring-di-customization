package com.github.monosoul.fortuneteller.web;

import static java.util.stream.Stream.generate;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import com.github.monosoul.fortuneteller.common.ZodiacSign;
import com.github.monosoul.fortuneteller.domain.HoroscopeTeller;
import com.github.monosoul.fortuneteller.model.Horoscope;
import java.util.function.Function;
import java.util.stream.Stream;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;

class PersonalizedHoroscopeTellControllerTest {

    private static final int LIMIT = 10;

    @Mock
    private HoroscopeTeller horoscopeTeller;
    @Mock
    private Function<String, ZodiacSign> zodiacSignConverter;
    @Mock
    private Function<String, String> nameNormalizer;
    @Mock
    private Horoscope horoscope;
    @Mock
    private ZodiacSign zodiacSign;
    private String normalizedName;
    private PersonalizedHoroscopeTellController controller;

    @BeforeEach
    void setUp() {
        initMocks(this);

        normalizedName = randomAlphabetic(LIMIT);
        controller = new PersonalizedHoroscopeTellController(horoscopeTeller, zodiacSignConverter, nameNormalizer);

        when(horoscopeTeller.tell(any(ZodiacSign.class))).thenReturn(horoscope);
        when(zodiacSignConverter.apply(anyString())).thenReturn(zodiacSign);
        when(nameNormalizer.apply(anyString())).thenReturn(normalizedName);
    }

    @ParameterizedTest
    @MethodSource("nameAndSignStream")
    void tell(final String name, final String sign) {
        val actual = controller.tell(name, sign);

        verify(nameNormalizer).apply(name);
        verify(zodiacSignConverter).apply(sign);
        verify(horoscopeTeller).tell(zodiacSign);
        verifyNoMoreInteractions(nameNormalizer, zodiacSignConverter, horoscopeTeller);

        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isSameAs(normalizedName);
        assertThat(actual.getHoroscope()).isSameAs(horoscope);
    }

    private static Stream<Arguments> nameAndSignStream() {
        return generate(() ->
                of(randomAlphabetic(LIMIT), randomAlphabetic(LIMIT))
        ).limit(LIMIT);
    }
}