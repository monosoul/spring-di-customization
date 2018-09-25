package com.github.monosoul.fortuneteller.domain.impl;

import com.github.monosoul.fortuneteller.da.FortuneResponseRepository;
import com.github.monosoul.fortuneteller.da.PersonalDataRepository;
import com.github.monosoul.fortuneteller.domain.FortuneTeller;
import com.github.monosoul.fortuneteller.model.FortuneRequest;
import com.github.monosoul.fortuneteller.model.FortuneResponse;
import com.github.monosoul.fortuneteller.model.PersonalData;
import java.util.function.Function;
import org.springframework.stereotype.Service;

@Service
public final class Globa implements FortuneTeller {

    private final FortuneResponseRepository responseRepository;
    private final Function<FortuneRequest, PersonalData> personalDataExtractor;
    private final PersonalDataRepository personalDataRepository;

    public Globa(
            final FortuneResponseRepository responseRepository,
            final Function<FortuneRequest, PersonalData> personalDataExtractor,
            final PersonalDataRepository personalDataRepository
    ) {
        this.responseRepository = responseRepository;
        this.personalDataExtractor = personalDataExtractor;
        this.personalDataRepository = personalDataRepository;
    }

    @Override
    public FortuneResponse tell(FortuneRequest request) {
        personalDataRepository.save(
                personalDataExtractor.apply(request)
        );

        return responseRepository.get();
    }
}
