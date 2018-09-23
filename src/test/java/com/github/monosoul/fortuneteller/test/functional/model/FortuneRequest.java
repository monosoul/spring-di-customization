package com.github.monosoul.fortuneteller.test.functional.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FortuneRequest {
    String name;
    String zodiacSign;
    Integer age;
    String email;
}
