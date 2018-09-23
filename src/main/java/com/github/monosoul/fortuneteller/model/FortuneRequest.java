package com.github.monosoul.fortuneteller.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = FortuneRequest.FortuneRequestBuilder.class)
public class FortuneRequest {
    String name;
    String zodiacSign;
    Integer age;
    String email;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class FortuneRequestBuilder {
    }
}
