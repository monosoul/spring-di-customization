package com.github.monosoul.fortuneteller.test.functional.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = FortuneResponse.FortuneResponseBuilder.class)
public class FortuneResponse {
    String message;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class FortuneResponseBuilder {
    }
}
