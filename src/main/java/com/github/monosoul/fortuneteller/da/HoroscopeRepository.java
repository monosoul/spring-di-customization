package com.github.monosoul.fortuneteller.da;

import com.github.monosoul.fortuneteller.common.ZodiacSign;
import com.github.monosoul.fortuneteller.model.Horoscope;

public interface HoroscopeRepository {
    Horoscope get(ZodiacSign zodiacSign);
}
