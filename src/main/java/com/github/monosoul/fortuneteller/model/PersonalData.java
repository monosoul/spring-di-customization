package com.github.monosoul.fortuneteller.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PersonalData {
    String name;
    Integer age;
    String email;
}
