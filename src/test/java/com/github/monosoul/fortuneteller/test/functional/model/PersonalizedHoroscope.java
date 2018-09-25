package com.github.monosoul.fortuneteller.test.functional.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = PersonalizedHoroscope.PersonalizedHoroscopeBuilder.class)
public class PersonalizedHoroscope {
    String name;
    Horoscope horoscope;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class PersonalizedHoroscopeBuilder {

    }
}
