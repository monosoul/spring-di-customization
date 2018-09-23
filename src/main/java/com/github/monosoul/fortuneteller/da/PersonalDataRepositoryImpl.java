package com.github.monosoul.fortuneteller.da;

import com.github.monosoul.fortuneteller.model.PersonalData;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public final class PersonalDataRepositoryImpl implements PersonalDataRepository {
    private final static Logger LOGGER = getLogger(PersonalDataRepositoryImpl.class);

    @Override
    public void save(final PersonalData personalData) {
        LOGGER.info("Mwa-ha-ha! Your precious personal data ({}) is mine now!!!", personalData);
    }
}
