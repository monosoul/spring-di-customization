package com.github.monosoul.fortuneteller.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FortuneResponse {
    String message;
}
