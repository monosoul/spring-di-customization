package com.github.monosoul.fortuneteller.da;

import com.github.monosoul.fortuneteller.model.FortuneResponse;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Random;

@Repository
final class RandomFortuneResponseRepository implements FortuneResponseRepository {

    private static final Random RANDOM = new Random();
    private final List<String> responses;

    RandomFortuneResponseRepository(final List<String> responses) {
        this.responses = responses;
    }

    @Override
    public FortuneResponse get() {
        return FortuneResponse.builder().message(
                responses.get(
                        RANDOM.nextInt(responses.size())
                )
        ).build();
    }
}
