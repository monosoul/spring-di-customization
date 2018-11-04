package com.github.monosoul.fortuneteller.domain.impl.horoscopeteller;

import static ch.qos.logback.classic.Level.DEBUG;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.slf4j.LoggerFactory.getLogger;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import com.github.monosoul.fortuneteller.common.ZodiacSign;
import com.github.monosoul.fortuneteller.domain.HoroscopeTeller;
import com.github.monosoul.fortuneteller.model.Horoscope;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

class LoggingHoroscopeTellerTest {

    @Mock
    private HoroscopeTeller internal;
    @Mock
    private ZodiacSign sign;
    @Mock
    private Horoscope horoscope;
    @Mock
    private Appender<ILoggingEvent> appender;
    @Captor
    private ArgumentCaptor<ILoggingEvent> captor;

    @BeforeEach
    void setUp() {
        initMocks(this);

        when(internal.tell(sign)).thenReturn(horoscope);
        configureLogger();
    }

    @Test
    void tell() {
        val actual = new LoggingHoroscopeTeller(internal).tell(sign);

        verify(internal).tell(sign);
        verify(appender, times(2)).doAppend(captor.capture());
        verifyNoMoreInteractions(internal, appender);

        assertThat(actual).isSameAs(horoscope);

        val events = captor.getAllValues().stream().map(ILoggingEvent::getFormattedMessage);
        assertThat(events).containsExactly(
                format("Got sign: %s", sign),
                format("Going to answer: %s", horoscope)
        );
    }

    private void configureLogger() {
        val logger = (Logger) getLogger(internal.getClass());
        logger.addAppender(appender);
        logger.setLevel(DEBUG);
    }
}