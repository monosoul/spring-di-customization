package com.github.monosoul.fortuneteller.domain;

import com.github.monosoul.fortuneteller.model.FortuneRequest;
import com.github.monosoul.fortuneteller.model.FortuneResponse;

public interface FortuneTeller {
    default FortuneResponse tell(FortuneRequest request) {
        return FortuneResponse.builder().message("I don't know anything").build();
    }
}
