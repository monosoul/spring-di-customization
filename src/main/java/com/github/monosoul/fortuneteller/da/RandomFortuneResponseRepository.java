package com.github.monosoul.fortuneteller.da;

import com.github.monosoul.fortuneteller.model.FortuneResponse;

import java.util.List;
import java.util.Random;

final class RandomFortuneResponseRepository implements FortuneResponseRepository {

    private static final Random RANDOM = new Random();
    private final List<FortuneResponse> responses;

    RandomFortuneResponseRepository(List<FortuneResponse> responses) {
        this.responses = responses;
    }

    @Override
    public FortuneResponse get() {
        return responses.get(
                RANDOM.nextInt(responses.size())
        );
    }
}
