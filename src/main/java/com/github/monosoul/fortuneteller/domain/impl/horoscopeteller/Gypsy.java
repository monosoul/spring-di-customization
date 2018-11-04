package com.github.monosoul.fortuneteller.domain.impl.horoscopeteller;

import static com.github.monosoul.fortuneteller.spring.DecoratorType.NOT_DECORATOR;
import com.github.monosoul.fortuneteller.common.ZodiacSign;
import com.github.monosoul.fortuneteller.da.HoroscopeRepository;
import com.github.monosoul.fortuneteller.domain.HoroscopeTeller;
import com.github.monosoul.fortuneteller.model.Horoscope;
import com.github.monosoul.fortuneteller.spring.qualifier.Decorator;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Decorator(NOT_DECORATOR)
@Service
public final class Gypsy implements HoroscopeTeller {

    private final HoroscopeRepository repository;

    public Gypsy(@NonNull final HoroscopeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Horoscope tell(final ZodiacSign sign) {
        return repository.get(sign);
    }
}
