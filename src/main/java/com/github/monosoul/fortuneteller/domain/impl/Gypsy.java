package com.github.monosoul.fortuneteller.domain.impl;

import com.github.monosoul.fortuneteller.common.ZodiacSign;
import com.github.monosoul.fortuneteller.da.HoroscopeRepository;
import com.github.monosoul.fortuneteller.domain.HoroscopeTeller;
import com.github.monosoul.fortuneteller.model.Horoscope;
import lombok.NonNull;
import org.springframework.stereotype.Service;

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
