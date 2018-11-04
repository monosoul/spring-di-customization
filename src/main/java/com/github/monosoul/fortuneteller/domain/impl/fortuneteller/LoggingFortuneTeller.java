package com.github.monosoul.fortuneteller.domain.impl.fortuneteller;

import static org.slf4j.LoggerFactory.getLogger;
import com.github.monosoul.fortuneteller.domain.FortuneTeller;
import com.github.monosoul.fortuneteller.model.FortuneRequest;
import com.github.monosoul.fortuneteller.model.FortuneResponse;
import lombok.NonNull;
import lombok.val;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public final class LoggingFortuneTeller implements FortuneTeller {

    private final FortuneTeller internal;
    private final Logger logger;

    public LoggingFortuneTeller(@NonNull final FortuneTeller internal) {
        this.internal = internal;
        this.logger = getLogger(internal.getClass());
    }

    @Override
    public FortuneResponse tell(final FortuneRequest request) {
        logger.info("Got request: {}", request);

        val response = internal.tell(request);

        logger.info("Going to answer: {}", response);

        return response;
    }
}
