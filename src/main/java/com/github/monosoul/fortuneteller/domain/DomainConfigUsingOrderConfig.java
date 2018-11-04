package com.github.monosoul.fortuneteller.domain;

import static java.util.Arrays.asList;
import com.github.monosoul.fortuneteller.domain.impl.fortuneteller.CachingFortuneTeller;
import com.github.monosoul.fortuneteller.domain.impl.fortuneteller.Globa;
import com.github.monosoul.fortuneteller.domain.impl.fortuneteller.LoggingFortuneTeller;
import com.github.monosoul.fortuneteller.domain.impl.horoscopeteller.CachingHoroscopeTeller;
import com.github.monosoul.fortuneteller.domain.impl.horoscopeteller.Gypsy;
import com.github.monosoul.fortuneteller.domain.impl.horoscopeteller.LoggingHoroscopeTeller;
import com.github.monosoul.fortuneteller.spring.order.OrderConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfigUsingOrderConfig {

    @Bean
    public OrderConfig<FortuneTeller> fortuneTellerOrderConfig() {
        return () -> asList(
                LoggingFortuneTeller.class,
                CachingFortuneTeller.class,
                Globa.class
        );
    }

    @Bean
    public OrderConfig<HoroscopeTeller> horoscopeTellerOrderConfig() {
        return () -> asList(
                LoggingHoroscopeTeller.class,
                CachingHoroscopeTeller.class,
                Gypsy.class
        );
    }
}
