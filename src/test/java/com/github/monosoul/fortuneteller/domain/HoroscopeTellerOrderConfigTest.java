package com.github.monosoul.fortuneteller.domain;

import static org.assertj.core.api.Assertions.assertThat;
import com.github.monosoul.fortuneteller.domain.impl.horoscopeteller.CachingHoroscopeTeller;
import com.github.monosoul.fortuneteller.domain.impl.horoscopeteller.Gypsy;
import com.github.monosoul.fortuneteller.domain.impl.horoscopeteller.LoggingHoroscopeTeller;
import lombok.val;
import org.junit.jupiter.api.Test;

class HoroscopeTellerOrderConfigTest {

    @Test
    void getClasses() {
        val actual = new HoroscopeTellerOrderConfig().getClasses();

        assertThat(actual).isNotNull()
                          .containsExactly(
                                  LoggingHoroscopeTeller.class,
                                  CachingHoroscopeTeller.class,
                                  Gypsy.class
                          );
    }
}