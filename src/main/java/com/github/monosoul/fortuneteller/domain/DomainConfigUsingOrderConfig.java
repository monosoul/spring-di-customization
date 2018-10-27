package com.github.monosoul.fortuneteller.domain;

import static java.util.Arrays.asList;
import com.github.monosoul.fortuneteller.domain.impl.CachingFortuneTeller;
import com.github.monosoul.fortuneteller.domain.impl.Globa;
import com.github.monosoul.fortuneteller.domain.impl.LoggingFortuneTeller;
import com.github.monosoul.fortuneteller.spring.order.OrderConfig;
import java.util.List;
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
}
