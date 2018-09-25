package com.github.monosoul.fortuneteller.test.functional.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = Horoscope.HoroscopeBuilder.class)
public class Horoscope {
    String message;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class HoroscopeBuilder {

    }
}
