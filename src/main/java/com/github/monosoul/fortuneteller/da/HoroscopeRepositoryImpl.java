package com.github.monosoul.fortuneteller.da;

import com.github.monosoul.fortuneteller.common.ZodiacSign;
import com.github.monosoul.fortuneteller.model.Horoscope;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class HoroscopeRepositoryImpl implements HoroscopeRepository {

    static final String DOGE_RESPONSE = "Wow! Such luck! Much money!";

    private final Map<ZodiacSign, String> horoscopes;

    public HoroscopeRepositoryImpl(@NonNull final Map<ZodiacSign, String> horoscopes) {
        this.horoscopes = horoscopes;
    }

    @Override
    public Horoscope get(final ZodiacSign zodiacSign) {
        return Horoscope.builder().message(
                horoscopes.getOrDefault(zodiacSign, DOGE_RESPONSE)
        ).build();
    }
}
