package com.github.monosoul.fortuneteller.domain;

import com.github.monosoul.fortuneteller.common.ZodiacSign;
import com.github.monosoul.fortuneteller.model.Horoscope;

public interface HoroscopeTeller {
    Horoscope tell(ZodiacSign sign);
}
