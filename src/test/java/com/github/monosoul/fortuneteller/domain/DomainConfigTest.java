package com.github.monosoul.fortuneteller.domain;

import static org.assertj.core.api.Assertions.assertThat;
import com.github.monosoul.fortuneteller.domain.impl.fortuneteller.CachingFortuneTeller;
import com.github.monosoul.fortuneteller.domain.impl.fortuneteller.Globa;
import com.github.monosoul.fortuneteller.domain.impl.fortuneteller.LoggingFortuneTeller;
import com.github.monosoul.fortuneteller.domain.impl.horoscopeteller.CachingHoroscopeTeller;
import com.github.monosoul.fortuneteller.domain.impl.horoscopeteller.Gypsy;
import com.github.monosoul.fortuneteller.domain.impl.horoscopeteller.LoggingHoroscopeTeller;
import lombok.val;
import org.junit.jupiter.api.Test;

class DomainConfigTest {

    @Test
    void fortuneTellerOrderConfig() {
        val actual = new DomainConfig().fortuneTellerOrderConfig();

        assertThat(actual).isNotNull();
        assertThat(actual.getClasses()).containsExactly(
                LoggingFortuneTeller.class,
                CachingFortuneTeller.class,
                Globa.class
        );
    }

    @Test
    void horoscopeTellerOrderConfig() {
        val actual = new DomainConfig().horoscopeTellerOrderConfig();

        assertThat(actual).isNotNull();
        assertThat(actual.getClasses()).containsExactly(
                LoggingHoroscopeTeller.class,
                CachingHoroscopeTeller.class,
                Gypsy.class
        );
    }
}