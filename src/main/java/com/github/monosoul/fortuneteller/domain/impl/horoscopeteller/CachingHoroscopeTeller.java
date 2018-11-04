package com.github.monosoul.fortuneteller.domain.impl.horoscopeteller;

import com.github.monosoul.fortuneteller.common.ZodiacSign;
import com.github.monosoul.fortuneteller.domain.HoroscopeTeller;
import com.github.monosoul.fortuneteller.model.Horoscope;
import java.util.Map;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public final class CachingHoroscopeTeller implements HoroscopeTeller {

    private final HoroscopeTeller internal;
    private final Map<ZodiacSign, Horoscope> cache;

    public CachingHoroscopeTeller(
            @Qualifier("gypsy")
            @NonNull final HoroscopeTeller internal,
            @NonNull final Map<ZodiacSign, Horoscope> cache
    ) {
        this.internal = internal;
        this.cache = cache;
    }

    @Override
    public Horoscope tell(final ZodiacSign sign) {
        return cache.computeIfAbsent(sign, internal::tell);
    }
}
