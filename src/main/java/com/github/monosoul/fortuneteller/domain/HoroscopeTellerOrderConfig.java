package com.github.monosoul.fortuneteller.domain;

import static java.util.Arrays.asList;
import com.github.monosoul.fortuneteller.domain.impl.horoscopeteller.CachingHoroscopeTeller;
import com.github.monosoul.fortuneteller.domain.impl.horoscopeteller.Gypsy;
import com.github.monosoul.fortuneteller.domain.impl.horoscopeteller.LoggingHoroscopeTeller;
import com.github.monosoul.fortuneteller.spring.order.OrderConfig;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("component")
final class HoroscopeTellerOrderConfig implements OrderConfig<HoroscopeTeller> {

    @Override
    public List<Class<? extends HoroscopeTeller>> getClasses() {
        return asList(
                LoggingHoroscopeTeller.class,
                CachingHoroscopeTeller.class,
                Gypsy.class
        );
    }
}
