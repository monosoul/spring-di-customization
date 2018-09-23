package com.github.monosoul.fortuneteller.domain;

import com.github.monosoul.fortuneteller.model.FortuneRequest;
import com.github.monosoul.fortuneteller.model.PersonalData;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public final class PersonalDataExtractor implements Function<FortuneRequest, PersonalData> {
    @Override
    public PersonalData apply(final FortuneRequest request) {
        return PersonalData.builder()
                .name(request.getName())
                .age(request.getAge())
                .email(request.getEmail())
                .build();
    }
}
