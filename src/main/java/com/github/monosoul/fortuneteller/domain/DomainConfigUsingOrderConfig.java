package com.github.monosoul.fortuneteller.domain;

import com.github.monosoul.fortuneteller.domain.impl.CachingFortuneTeller;
import com.github.monosoul.fortuneteller.domain.impl.Globa;
import com.github.monosoul.fortuneteller.domain.impl.LoggingFortuneTeller;
import com.github.monosoul.fortuneteller.spring.order.OrderConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings("unchecked")
@Configuration
public class DomainConfigUsingOrderConfig {

    @Bean
    public OrderConfig<FortuneTeller> fortuneTellerOrderConfig() {
        return new OrderConfig<>(
                Globa.class,
                CachingFortuneTeller.class,
                LoggingFortuneTeller.class
        );
    }
}
