package com.github.monosoul.fortuneteller.domain.impl.horoscopeteller;

import static org.slf4j.LoggerFactory.getLogger;
import com.github.monosoul.fortuneteller.common.ZodiacSign;
import com.github.monosoul.fortuneteller.domain.HoroscopeTeller;
import com.github.monosoul.fortuneteller.model.Horoscope;
import lombok.NonNull;
import lombok.val;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public final class LoggingHoroscopeTeller implements HoroscopeTeller {

    private final HoroscopeTeller internal;
    private final Logger logger;

    public LoggingHoroscopeTeller(
            @NonNull final HoroscopeTeller internal
    ) {
        this.internal = internal;
        this.logger = getLogger(internal.getClass());
    }

    @Override
    public Horoscope tell(final ZodiacSign sign) {
        logger.info("Got sign: {}", sign);

        val response = internal.tell(sign);

        logger.info("Going to answer: {}", response);

        return response;
    }
}
