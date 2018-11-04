package com.github.monosoul.fortuneteller.domain;

import com.github.monosoul.fortuneteller.da.FortuneResponseRepository;
import com.github.monosoul.fortuneteller.da.PersonalDataRepository;
import com.github.monosoul.fortuneteller.domain.impl.fortuneteller.CachingFortuneTeller;
import com.github.monosoul.fortuneteller.domain.impl.fortuneteller.Globa;
import com.github.monosoul.fortuneteller.domain.impl.fortuneteller.LoggingFortuneTeller;
import com.github.monosoul.fortuneteller.model.FortuneRequest;
import com.github.monosoul.fortuneteller.model.FortuneResponse;
import com.github.monosoul.fortuneteller.model.PersonalData;
import org.springframework.context.annotation.Bean;

import java.util.Map;
import java.util.function.Function;

//@Configuration
public class DomainConfig {

    @Bean
    public FortuneTeller fortuneTeller(
            final Map<FortuneRequest, FortuneResponse> cache,
            final FortuneResponseRepository fortuneResponseRepository,
            final Function<FortuneRequest, PersonalData> personalDataExtractor,
            final PersonalDataRepository personalDataRepository
    ) {
        return new LoggingFortuneTeller(
                new CachingFortuneTeller(
                        new Globa(fortuneResponseRepository, personalDataExtractor, personalDataRepository),
                        cache
                )
        );
    }
}
