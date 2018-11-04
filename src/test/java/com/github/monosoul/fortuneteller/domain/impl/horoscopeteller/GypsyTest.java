package com.github.monosoul.fortuneteller.domain.impl.horoscopeteller;

import static com.github.monosoul.fortuneteller.util.Util.randomEnum;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import com.github.monosoul.fortuneteller.common.ZodiacSign;
import com.github.monosoul.fortuneteller.da.HoroscopeRepository;
import com.github.monosoul.fortuneteller.model.Horoscope;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class GypsyTest {

    @Mock
    private HoroscopeRepository repository;
    @Mock
    private Horoscope horoscope;

    @BeforeEach
    void setUp() {
        initMocks(this);

        when(repository.get(any(ZodiacSign.class))).thenReturn(horoscope);
    }

    @Test
    void tell() {
        val sign = randomEnum(ZodiacSign.class);

        val actual = new Gypsy(repository).tell(sign);

        verify(repository).get(sign);
        verifyNoMoreInteractions(repository);

        assertThat(actual).isSameAs(horoscope);
    }
}