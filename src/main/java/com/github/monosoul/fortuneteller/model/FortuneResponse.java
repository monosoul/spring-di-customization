package com.github.monosoul.fortuneteller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FortuneResponse {
    String message;
}
