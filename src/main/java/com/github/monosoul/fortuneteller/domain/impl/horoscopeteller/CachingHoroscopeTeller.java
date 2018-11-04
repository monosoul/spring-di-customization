package com.github.monosoul.fortuneteller.domain.impl.horoscopeteller;

import static com.github.monosoul.fortuneteller.spring.DecoratorType.CACHING;
import static com.github.monosoul.fortuneteller.spring.DecoratorType.NOT_DECORATOR;
import com.github.monosoul.fortuneteller.common.ZodiacSign;
import com.github.monosoul.fortuneteller.domain.HoroscopeTeller;
import com.github.monosoul.fortuneteller.model.Horoscope;
import com.github.monosoul.fortuneteller.spring.qualifier.Decorator;
import java.util.Map;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Decorator(CACHING)
@Component
public final class CachingHoroscopeTeller implements HoroscopeTeller {

    private final HoroscopeTeller internal;
    private final Map<ZodiacSign, Horoscope> cache;

    public CachingHoroscopeTeller(
            @Decorator(NOT_DECORATOR)
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
