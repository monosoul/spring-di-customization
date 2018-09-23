package com.github.monosoul.fortuneteller.da;

import com.github.monosoul.fortuneteller.model.FortuneResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

@Configuration
public class DaConfig {

    @Bean
    public FortuneResponseRepository randomFortuneResponseRepository() {
        return new RandomFortuneResponseRepository(fortuneResponses(
                "You'll get married!",
                "Wow! You'll be very rich!",
                "You'll have 10 children!",
                "You'll meet your love soon!",
                "You'll get a promotion at your work in a year!",
                "You'll become very popular soon!"
        ));
    }

    private List<FortuneResponse> fortuneResponses(final String... messages) {
        return stream(messages)
                .map(m -> FortuneResponse.builder().message(m).build())
                .collect(toList());
    }
}
