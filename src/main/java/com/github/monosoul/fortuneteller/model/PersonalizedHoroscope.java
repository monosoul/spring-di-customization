package com.github.monosoul.fortuneteller.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class PersonalizedHoroscope {
    String name;
    Horoscope horoscope;
}
