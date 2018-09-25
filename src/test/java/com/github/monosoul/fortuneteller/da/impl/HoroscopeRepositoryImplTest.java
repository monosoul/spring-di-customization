package com.github.monosoul.fortuneteller.da.impl;

import static com.github.monosoul.fortuneteller.da.impl.HoroscopeRepositoryImpl.DOGE_RESPONSE;
import static com.github.monosoul.fortuneteller.util.Util.randomEnum;
import static java.util.stream.Stream.generate;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import com.github.monosoul.fortuneteller.common.ZodiacSign;
import java.util.Map;
import java.util.stream.Stream;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

class HoroscopeRepositoryImplTest {

    private static final int LIMIT = 10;

    @Mock
    private Map<ZodiacSign, String> horoscopes;
    @Captor
    private ArgumentCaptor<String> captor;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @ParameterizedTest
    @MethodSource("horoscopeAndSignStream")
    void get(final String horoscope, final ZodiacSign sign) {
        when(horoscopes.getOrDefault(any(ZodiacSign.class), anyString())).thenReturn(horoscope);

        val actual = new HoroscopeRepositoryImpl(horoscopes).get(sign);

        verify(horoscopes).getOrDefault(eq(sign), captor.capture());
        verifyNoMoreInteractions(horoscopes);

        assertThat(actual).isNotNull();
        assertThat(actual.getMessage()).isEqualTo(horoscope);
        assertThat(captor.getValue()).isEqualTo(DOGE_RESPONSE);
    }

    private static Stream<Arguments> horoscopeAndSignStream() {
        return generate(() -> (Arguments) () ->
                new Object[]{randomAlphabetic(LIMIT), randomEnum(ZodiacSign.class)}
        ).limit(LIMIT);
    }
}