package com.github.monosoul.fortuneteller.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;
import com.github.monosoul.fortuneteller.common.ZodiacSign;
import com.github.monosoul.fortuneteller.model.FortuneRequest;
import com.github.monosoul.fortuneteller.model.FortuneResponse;
import com.github.monosoul.fortuneteller.model.Horoscope;
import java.util.concurrent.ConcurrentHashMap;
import lombok.val;
import org.junit.jupiter.api.Test;

class CacheConfigTest {

    @Test
    void fortuneResponseCache() {
        val actual = new CacheConfig().fortuneResponseCache();
        val expected = new ConcurrentHashMap<FortuneRequest, FortuneResponse>();

        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
    }

    @Test
    void horoscopeCache() {
        val actual = new CacheConfig().horoscopeCache();
        val expected = new ConcurrentHashMap<ZodiacSign, Horoscope>();

        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
    }
}