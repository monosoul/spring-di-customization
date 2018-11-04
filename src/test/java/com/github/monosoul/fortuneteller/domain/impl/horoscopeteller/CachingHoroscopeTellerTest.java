package com.github.monosoul.fortuneteller.domain.impl.horoscopeteller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import com.github.monosoul.fortuneteller.common.ZodiacSign;
import com.github.monosoul.fortuneteller.domain.HoroscopeTeller;
import com.github.monosoul.fortuneteller.model.Horoscope;
import java.util.Map;
import java.util.function.Function;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

@SuppressWarnings("unchecked")
class CachingHoroscopeTellerTest {

    @Mock
    private HoroscopeTeller internal;
    @Mock
    private Map<ZodiacSign, Horoscope> cache;
    @Mock
    private ZodiacSign sign;
    @Mock
    private Horoscope horoscope;
    @Captor
    private ArgumentCaptor<Function> captor;

    @BeforeEach
    void setUp() {
        initMocks(this);

        when(internal.tell(sign)).thenReturn(horoscope);
        when(cache.computeIfAbsent(any(ZodiacSign.class), any(Function.class))).thenReturn(horoscope);
    }

    @Test
    void tell() {
        val actual = new CachingHoroscopeTeller(internal, cache).tell(sign);

        verify(cache).computeIfAbsent(eq(sign), captor.capture());
        verifyNoMoreInteractions(cache, internal);

        assertThat(actual).isSameAs(horoscope);

        val internalResponse = captor.getValue().apply(sign);
        verify(internal).tell(sign);
        verifyNoMoreInteractions(internal);

        assertThat(internalResponse).isSameAs(horoscope);
    }
}