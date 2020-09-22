package com.github.monosoul.fortuneteller.domain;

import com.github.monosoul.fortuneteller.domain.impl.fortuneteller.CachingFortuneTeller;
import com.github.monosoul.fortuneteller.domain.impl.fortuneteller.Globa;
import com.github.monosoul.fortuneteller.domain.impl.fortuneteller.LoggingFortuneTeller;
import com.github.monosoul.fortuneteller.domain.impl.horoscopeteller.CachingHoroscopeTeller;
import com.github.monosoul.fortuneteller.domain.impl.horoscopeteller.Gypsy;
import com.github.monosoul.fortuneteller.domain.impl.horoscopeteller.LoggingHoroscopeTeller;
import com.github.monosoul.spring.order.OrderConfig;
import com.github.monosoul.spring.order.support.OrderConfigConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


import static com.github.monosoul.spring.order.OrderConfigItemImpl.of;
import static java.util.Arrays.asList;

@Configuration
@Import(OrderConfigConfiguration.class)
public class DomainConfig {

    @Bean
    public OrderConfig<FortuneTeller> fortuneTellerOrderConfig() {
        return () -> asList(
                of(LoggingFortuneTeller.class),
                of(CachingFortuneTeller.class),
                of(Globa.class)
        );
    }

    @Bean
    public OrderConfig<HoroscopeTeller> horoscopeTellerOrderConfig() {
        return () -> asList(
                of(LoggingHoroscopeTeller.class),
                of(CachingHoroscopeTeller.class),
                of(Gypsy.class)
        );
    }
}
