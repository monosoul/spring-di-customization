package com.github.monosoul.fortuneteller.da.impl;

import com.github.monosoul.fortuneteller.da.PersonalDataRepository;
import com.github.monosoul.fortuneteller.model.PersonalData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public final class PersonalDataRepositoryImpl implements PersonalDataRepository {

    @Override
    public void save(final PersonalData personalData) {
        log.info("Mwa-ha-ha! Your precious personal data ({}) is mine now!!!", personalData);
    }
}
