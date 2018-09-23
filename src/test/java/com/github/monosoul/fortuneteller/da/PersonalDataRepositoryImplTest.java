package com.github.monosoul.fortuneteller.da;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import com.github.monosoul.fortuneteller.model.PersonalData;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static ch.qos.logback.classic.Level.DEBUG;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.slf4j.LoggerFactory.getLogger;

class PersonalDataRepositoryImplTest {

    @Mock
    private PersonalData personalData;
    @Mock
    private Appender<ILoggingEvent> appender;
    @Captor
    private ArgumentCaptor<ILoggingEvent> captor;

    @BeforeEach
    void setUp() {
        initMocks(this);

        configureLogger();
    }

    @Test
    void save() {
        new PersonalDataRepositoryImpl().save(personalData);

        verify(appender).doAppend(captor.capture());

        assertThat(captor.getValue().getFormattedMessage())
                .isEqualTo(format("Mwa-ha-ha! Your precious personal data (%s) is mine now!!!", personalData));
    }

    private void configureLogger() {
        val logger = (Logger) getLogger(PersonalDataRepositoryImpl.class);
        logger.addAppender(appender);
        logger.setLevel(DEBUG);
    }
}