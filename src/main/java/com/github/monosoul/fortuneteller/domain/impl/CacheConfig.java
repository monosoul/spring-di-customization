package com.github.monosoul.fortuneteller.domain.impl;

import com.github.monosoul.fortuneteller.common.ZodiacSign;
import com.github.monosoul.fortuneteller.model.FortuneRequest;
import com.github.monosoul.fortuneteller.model.FortuneResponse;
import com.github.monosoul.fortuneteller.model.Horoscope;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Bean
    public Map<FortuneRequest, FortuneResponse> fortuneResponseCache() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public Map<ZodiacSign, Horoscope> horoscopeCache() {
        return new ConcurrentHashMap<>();
    }
}
