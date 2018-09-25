package com.github.monosoul.fortuneteller.web.converter;

import com.github.monosoul.fortuneteller.common.ZodiacSign;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public final class ZodiacSignConverter implements Function<String, ZodiacSign> {

    @Override
    public ZodiacSign apply(@NonNull final String input) {
        return ZodiacSign.valueOf(input.toUpperCase());
    }
}
